package com.indien.indien_backend.service;

import com.indien.indien_backend.config.security.SecurityUtil;
import com.indien.indien_backend.dto.member.MemberResponseDto;
import com.indien.indien_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService
{
    private final MemberRepository memberRepository;

    public MemberResponseDto getMemberInfo(String email){
        return memberRepository.findByEmail(email)
            .map(MemberResponseDto::of)
            .orElseThrow(()-> new RuntimeException("유저 정보가 없음."));

    }
    public MemberResponseDto getMyInfo(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
            .map(MemberResponseDto::of)
            .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

}
