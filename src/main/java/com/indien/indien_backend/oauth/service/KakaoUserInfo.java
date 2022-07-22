package com.indien.indien_backend.oauth.service;

import java.util.Map;

import com.indien.indien_backend.oauth.Oauth2UserInfo;

public class KakaoUserInfo implements Oauth2UserInfo
{
    private final Map<String, Object> attributes;
    public KakaoUserInfo(Map<String, Object> attributes)
    {
        this.attributes=attributes;
    }

    @Override
    public String getProviderMemberId()
    {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider()
    {
        return "kakao";
    }

    @Override
    public String getProviderId()
    {
        return attributes.get("sub").toString();
    }

    @Override
    public String getImgUrl()
    {
        return (String) getProfile().get("profile_image_url");
    }

    @Override
    public String getEmail()
    {
        return (String) getKakaoAccount().get("email");
    }

    @Override
    public String getNickname()
    {
        return (String) getProfile().get("nickname");
    }


    public Map<String,Object> getKakaoAccount(){
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String,Object> getProfile(){
        return (Map<String, Object>) getKakaoAccount().get("profile");
    }
}
