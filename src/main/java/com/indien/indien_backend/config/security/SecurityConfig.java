package com.indien.indien_backend.config.security;

import com.indien.indien_backend.config.jwt.JwtSecurityConfig;
import com.indien.indien_backend.jwt.JwtAccessDeniedHandler;
import com.indien.indien_backend.jwt.JwtAuthenticationEntryPoint;
import com.indien.indien_backend.jwt.TokenProvider;
import com.indien.indien_backend.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // 무시할 url
        web.ignoring()
            .antMatchers("/h2-console/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()

            // exception handling 할 때 만들 클래스 추가
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            // h2-console 을 위한 설정을 추가
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            // 시큐리티는 기본적으로 세션을 사용
            // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
            .and()
            .authorizeHttpRequests()
            .antMatchers("/**","/oauth/**").permitAll()
            .anyRequest().authenticated()

            .and()
            .apply(new JwtSecurityConfig(tokenProvider))
        ;

    }
}
