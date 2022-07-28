package com.indien.indien_backend.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import com.indien.indien_backend.controller.response.OAuthTokenResponse;
import com.indien.indien_backend.domain.RefreshToken;
import com.indien.indien_backend.domain.common.Authority;
import com.indien.indien_backend.domain.member.Member;
import com.indien.indien_backend.dto.MemberLoginDto;
import com.indien.indien_backend.dto.jwt.TokenDto;
import com.indien.indien_backend.jwt.TokenProvider;
import com.indien.indien_backend.oauth.Oauth2UserInfo;
import com.indien.indien_backend.repository.MemberRepository;
import com.indien.indien_backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OAuthService
{
    public static final String BEARER_TYPE = "Bearer";
    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public TokenDto login(String providerName, String code)
    {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
        OAuthTokenResponse tokenResponse = getToken(code,provider);
        Member userProfile = getUserProfile(providerName, tokenResponse, provider);

        MemberLoginDto memberLoginDto = new MemberLoginDto(userProfile.getEmail(), userProfile.getPasswd(), userProfile.getProvider());
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginDto.toAuthentication();

        Authentication authentication = null;
        try
        {
            authentication= authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        }

        catch (UsernameNotFoundException e){
            e.printStackTrace();
        }

        //[Back]
        // return token
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
            .key(authentication.getName())
            .value(tokenDto.getRefreshToken())
            .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    /*
    * Resource Server에서 받아온 User정보를 가공
    */
    private Member getUserProfile(String providerName, OAuthTokenResponse tokenResponse, ClientRegistration provider)
    {
        Map<String,Object> userAttributes = getUserAttributes(provider,tokenResponse);
        Oauth2UserInfo oauth2UserInfo = null;
        if("kakao".equals(providerName)){
            oauth2UserInfo = new KakaoUserInfo(userAttributes);
        }
        else{
            log.info("허용되지 않은 접근입니다.");
        }
        assert oauth2UserInfo != null;

        String provide = oauth2UserInfo.getProvider();
//        String providerId = oauth2UserInfo.getProviderId();
        String email = oauth2UserInfo.getEmail();
        String nickName = oauth2UserInfo.getNickname();
//[BACK]
// OauthService 에서 Security 로직을 탈 필요가 없다.
// 그 이유는 authorization code로 token 을 받아오고
// 해당 토큰으로 유저 정보를 가져오고
// 해당 유저 정보로 access 토큰을 만들어 클라이언트에게 전달해주면 끝이기 때문.
// return member or new member
        Member member = memberRepository.findByEmailAndProvider(email, provide)
            .orElse(Member.builder()
                .email(email)
                .provider(provide)
                .authority(Authority.ROLE_USER)
                .passwd(passwordEncoder.encode(email + provide))
                .build());

        memberRepository.save(member);

        return member;
    }

    /*
    * Resource Server 에서 받은 Token으로 User정보 받아오기
    */
    private Map<String, Object> getUserAttributes(ClientRegistration provider, OAuthTokenResponse tokenResponse)
    {
        return WebClient.create()
            .get()
            .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
            .headers(header -> header.setBearerAuth(tokenResponse.getAccess_token()))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String,Object>>(){})
            .block();
    }

    /*
    * 인가코드(code)로 Resource Server 에서 토큰 받아오기
    */
    private OAuthTokenResponse getToken(String code, ClientRegistration provider){
        return WebClient.create()
            .post()
            .uri(provider.getProviderDetails().getTokenUri())
            .headers(header -> {
                header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
            })
            .bodyValue(tokenRequest(code,provider))
            .retrieve()
            .bodyToMono(OAuthTokenResponse.class)
            .block();
    }
    private MultiValueMap<String,String> tokenRequest(String code, ClientRegistration provider){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code",code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri",provider.getRedirectUri());
        formData.add("client_secret",provider.getClientSecret());
        formData.add("client_id",provider.getClientId());
        return formData;
    }
}
