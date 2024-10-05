package zaza.techblog.global.common.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusCodeTest {

    @DisplayName("codename 값을 StatusCode로 변환한다. 변환할 값이 없을 때는 null 값을 반환한다.")
    @Test
    void getStatusCodeByStringValue(){

    // given
        String active = "active";
        String inactive = "inactive";
        String deleted = "deleted";
        String othersValue = "others value";
        String emptyValue = "";

    // when
        StatusCode activeStatusCode = StatusCode.of(active);
        StatusCode inactiveStatusCode = StatusCode.of(inactive);
        StatusCode deletedStatusCode = StatusCode.of(deleted);
        StatusCode othersStatusCode = StatusCode.of(othersValue);
        StatusCode emptyStatusCode = StatusCode.of(emptyValue);

    // then
        assertEquals(activeStatusCode, StatusCode.ACTIVE);
        assertEquals(inactiveStatusCode, StatusCode.INACTIVE);
        assertEquals(deletedStatusCode, StatusCode.DELETED);
        assertNull(othersStatusCode);
        assertNull(emptyStatusCode);
    }

}