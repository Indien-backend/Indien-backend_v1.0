package com.indien.indien_backend.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import com.indien.indien_backend.controller.response.LoginResponse;
import com.indien.indien_backend.controller.response.OAuthTokenResponse;
import com.indien.indien_backend.jwt.TokenProvider;
import com.indien.indien_backend.oauth.Oauth2UserInfo;
import com.indien.indien_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>
{
    public static final String BEARER_TYPE = "Bearer";
    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        return null;
    }

    @Transactional
    public LoginResponse login(String providerName, String code)
    {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
        OAuthTokenResponse tokenResponse = getToken(code,provider);
        getUserProfile(providerName,tokenResponse,provider);
        return null;
    }

    private User getUserProfile(String providerName, OAuthTokenResponse tokenResponse, ClientRegistration provider)
    {
        Map<String,Object> userAttributes = getUserAttributes(provider,tokenResponse);
        Oauth2UserInfo oauth2UserInfo = null;
        if("kakao".equals(providerName)){
            oauth2UserInfo = new KakaoUserInfo(userAttributes);
        }
        else{
            log.info("허용되지 않은 접근입니다.");
        }
        return null;
    }

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
