package com.indien.indien_backend.dto.member;

import com.indien.indien_backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto
{
    private Long id;
    private String email;

    public static MemberResponseDto of(Member member){
        return new MemberResponseDto(member.getId(), member.getEmail());
    }

}
