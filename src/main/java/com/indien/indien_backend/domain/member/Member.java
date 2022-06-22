package com.indien.indien_backend.domain.member;

import java.time.Instant;
import javax.persistence.*;

import com.indien.indien_backend.domain.common.Authority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member
{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String passwd;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberDetail memberDetail;

    private Instant created;

    private Instant updated;

    @Builder
    public Member(String email, String passwd, Authority authority) {
        this.email = email;
        this.passwd = passwd;
        this.authority = authority;
    }
}
