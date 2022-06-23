package com.indien.indien_backend.oauth;

import java.util.Map;

public abstract class Oauth2UserInfo
{
    protected Map<String, Object> attributes;

    public Oauth2UserInfo(Map<String,Object> attributes){
        this.attributes = attributes;
    }
    public Map<String,Object> getAttributes(){
        return this.attributes;
    }
    public abstract String getProviderMemberId();
    public abstract String getProvider();
    public abstract String getImgUrl();
    public abstract String getEmail();
    public abstract String getNickname();
}
