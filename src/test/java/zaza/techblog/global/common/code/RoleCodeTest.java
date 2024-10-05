package zaza.techblog.global.common.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleCodeTest {

    @DisplayName("codename 값을 RoleCode로 변환한다. 변환할 값이 없을 때는 Anonymous를 반환한다.")
    @Test
    void getRoleCodeByStringValue(){

    // given
        String user = "user";
        String admin = "admin";
        String anonymous = "anonymous";
        String emptyValue = "";
        String otherValue = "other values";

    // when
        RoleCode userRoleCode = RoleCode.of(user);
        RoleCode adminRoleCode = RoleCode.of(admin);
        RoleCode anonymousRoleCode = RoleCode.of(anonymous);
        RoleCode emptyRoleCode = RoleCode.of(emptyValue);
        RoleCode otherRoleCode = RoleCode.of(otherValue);

    // then
        assertEquals(userRoleCode, RoleCode.ROLE_USER);
        assertEquals(adminRoleCode, RoleCode.ROLE_ADMIN);
        assertEquals(anonymousRoleCode, RoleCode.ROLE_ANONYMOUS);
        assertEquals(emptyRoleCode, RoleCode.ROLE_ANONYMOUS);
        assertEquals(otherRoleCode, RoleCode.ROLE_ANONYMOUS);
    }
}