package com.indien.indien_backend.domain.member;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail
{
    @Id @GeneratedValue
    @Column(name = "member_detail_id")
    private Long id;

    @OneToOne(mappedBy = "memberDetail", fetch = FetchType.LAZY)
    private Member member;

    @Column(length = 300)
    private String intro;

    @Column(name = "nick")
    private String nick;

    @Column(name = "sns_use_check", columnDefinition = "TINYINT(1)")
    private boolean snsUseCheck;

    @Column(name = "private_check", columnDefinition = "TINYINT(1)")
    private boolean privateCheck;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "background_img")
    private String backgroundImg;

    @Column(name = "notify_check", columnDefinition = "TINYINT(1)")
    private boolean notifyCheck;

    @Column(name = "follower_blind_check", columnDefinition = "TINYINT(1)")
    private boolean followerBlindCheck;

}
