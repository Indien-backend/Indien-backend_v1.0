package com.indien.indien_backend.controller;

import com.indien.indien_backend.controller.response.LoginResponse;
import com.indien.indien_backend.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController
{
    private final OAuthService oAuthService;

    @GetMapping("/login/{provider}")
    public ResponseEntity<LoginResponse> loginProvider(@PathVariable String provider, @RequestParam String code){
        System.out.println(code);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setCode(code);
        oAuthService.login(provider,code);
        return ResponseEntity.ok(loginResponse);
    }
}
