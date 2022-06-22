package com.indien.indien_backend.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OAuthTokenResponse
{
    private String token_type;
    private String access_token;
    private String id_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;
    private String scope;
}
