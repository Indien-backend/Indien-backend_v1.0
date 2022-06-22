package com.indien.indien_backend.controller;

import com.indien.indien_backend.controller.response.LoginResponse;
import com.indien.indien_backend.dto.MemberRequestDto;
import com.indien.indien_backend.dto.TokenRequestDto;
import com.indien.indien_backend.dto.jwt.TokenDto;
import com.indien.indien_backend.dto.member.MemberResponseDto;
import com.indien.indien_backend.oauth.service.OAuthService;
import com.indien.indien_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;
    private final OAuthService oAuthService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto){
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto){
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code){
        LoginResponse loginResponse = oAuthService.login(provider,code);
        return ResponseEntity.ok().body(loginResponse);
    }

}
