package com.indien.indien_backend.oauth;

import java.util.HashMap;
import java.util.Map;

public interface Oauth2UserInfo
{
    Map<String, Object> getAttributes = new HashMap<>();

    String getProviderMemberId();
    String getProvider();
    String getProviderId();
    String getImgUrl();
    String getEmail();
    String getNickname();
}
