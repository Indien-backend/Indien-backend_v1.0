package com.indien.indien_backend.controller;

import com.indien.indien_backend.dto.MemberLoginDto;
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
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberLoginDto memberLoginDto){
        return ResponseEntity.ok(authService.signup(memberLoginDto));
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberLoginDto memberLoginDto){
        return ResponseEntity.ok(authService.login(memberLoginDto));
    }
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<TokenDto> login(@PathVariable String provider, @RequestParam String code){
        TokenDto loginResponse = oAuthService.login(provider,code);
        return ResponseEntity.ok().body(loginResponse);
    }

}
