package com.indien.indien_backend.domain.member;

import java.time.Instant;
import javax.persistence.*;

import com.indien.indien_backend.domain.common.Authority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member
{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String passwd;

    @Comment("로그인 경로")
    private String provider;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    //[BACK]
    // MemberDetail를 테이블로 빼지말고 Eembeded 타입으로 정의
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberDetail memberDetail;

    private Instant created;

    private Instant updated;

    @Builder
    public Member(String email, String passwd, String provider, Authority authority) {
        this.email = email;
        this.passwd = passwd;
        this.provider = provider;
        this.authority = authority;
    }
}
