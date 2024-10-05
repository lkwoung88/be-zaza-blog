package zaza.techblog.global.common.member.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

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
}
