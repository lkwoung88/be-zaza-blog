package zaza.techblog.global.common.member.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.global.common.member.entity.Member;

@Getter
public class JoinMemberRequest {

    @NotNull
    @NotEmpty
    // TODO 정규식
    private String id;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    // TODO 정규식
    private String email;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .role(RoleCode.ROLE_USER)
                .status(StatusCode.ACTIVE)
                .oAuthProvider("")
                .build();
    }
}
