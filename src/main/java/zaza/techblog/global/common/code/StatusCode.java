package zaza.techblog.global.common.code;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
public enum StatusCode {
    ACTIVE("active", "활동"),
    INACTIVE("inactive", "활동정지"),
    DELETED("deleted", "탈퇴"),
    ;

    private final String codename;
    private final String comment;

    StatusCode(String codename, String comment) {
        this.codename = codename;
        this.comment = comment;
    }

    @Nullable
    public static StatusCode of(@NonNull final String codename) {

        codename.toLowerCase();

        for (StatusCode statusCode : values()) {
            if (codename.equals(statusCode.codename)) {
                return statusCode;
            }
        }

        return null;
    }
}
