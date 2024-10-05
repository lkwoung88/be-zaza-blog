package zaza.techblog.global.common.code;

import lombok.Getter;
import lombok.NonNull;

@Getter
public enum RoleCode {
    ROLE_ADMIN("admin","관리자"),
    ROLE_USER("user","유저"),
    ROLE_ANONYMOUS("anonymous","익명사용자")
    ;

    private String codename;
    private String comment;

    RoleCode(String codename, String comment) {
        this.codename = codename;
        this.comment = comment;
    }

    public static RoleCode of(@NonNull final String codename) {

        codename.toLowerCase();

        for (RoleCode roleCode : RoleCode.values()) {
            if (roleCode.codename.equals(codename)) {
                return roleCode;
            }
        }

        return ROLE_ANONYMOUS;
    }
}
