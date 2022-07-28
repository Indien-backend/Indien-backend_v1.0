package com.indien.indien_backend.dto;

import com.indien.indien_backend.domain.common.Authority;
import com.indien.indien_backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDto
{

    private String email;
    private String password;

    private String provider;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .email(email)
            .passwd(passwordEncoder.encode(password))
            .provider(provider)
            .authority(Authority.ROLE_USER)
            .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
