package zaza.techblog.global.common.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import zaza.techblog.global.auth.dto.SocialOAuthResponse;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.global.common.entity.DateEntity;

@Getter
@Entity
@Table(name = "t_cmn_member")
public class Member extends DateEntity {

    @Id
    @GeneratedValue
    private long number;

    private String id;

    private String password;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private RoleCode role;

    @Enumerated(EnumType.STRING)
    private StatusCode status;

    private String oAuthProvider;

    public Member() {}

    @Builder
    public Member(long number, String id, String password, String name, String email, RoleCode role, StatusCode status, String oAuthProvider) {
        this.number = number;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.oAuthProvider = oAuthProvider;
    }

    public Member(SocialOAuthResponse socialOAuthResponse) {
        this.name = socialOAuthResponse.getName();
        this.email = socialOAuthResponse.getEmail();
        this.id = socialOAuthResponse.getProvider() + "-" + socialOAuthResponse.getProviderId();
        this.role = RoleCode.ROLE_USER;
        this.status = StatusCode.ACTIVE;
        this.oAuthProvider = socialOAuthResponse.getProvider();
    }

    public void updateMemberByOAuthResponse(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
