package zaza.techblog.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private RoleCode role;
    private StatusCode status;
    private String oAuthProvider;

    @Builder
    public MemberDto(String id, String password, String name, String email, RoleCode role, StatusCode status, String oAuthProvider) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.oAuthProvider = oAuthProvider;
    }

    @Builder
    public MemberDto(String username, RoleCode role) {
        this.id = username;
        this.role = role;
    }

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .password(null)
                .name(member.getName())
                .email(member.getEmail())
                .role(member.getRole())
                .status(member.getStatus())
                .oAuthProvider(member.getOAuthProvider())
                .build();
    }
}
