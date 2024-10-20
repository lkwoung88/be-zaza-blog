package zaza.techblog.global.common.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {

    SUCCESS(2000, "작업을 성공했습니다."),
    SERVER_ERROR(5000, "서버에 오류가 발생했습니다. \n 잠시 후 다시 시도해주세요."),
    INPUT_ERROR(4000, "입력값을 확인해주세요."),
    CONFLICT_ERROR(4090, "중복된 값 입니다."),
    ;

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
