package com.indien.indien_backend.config.jwt;

import com.indien.indien_backend.jwt.JwtFilter;
import com.indien.indien_backend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
    private final TokenProvider tokenProvider;

    //TokenProvider 를 주입받아서 JwtFilter를 통해 Security 로직에 필터를 등록

    @Override
    public void configure(HttpSecurity builder) throws Exception
    {
        JwtFilter cusomFilter = new JwtFilter(tokenProvider);
        builder.addFilterBefore(cusomFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
