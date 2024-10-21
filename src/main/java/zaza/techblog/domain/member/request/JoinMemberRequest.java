package zaza.techblog.domain.member.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zaza.techblog.global.common.code.RoleCode;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.domain.member.entity.Member;

@Getter
@NoArgsConstructor
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

    @Builder
    public JoinMemberRequest(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

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
