package zaza.techblog.global.common.code;

public enum ResponseCode {

    SUCCESS(2000, "작업을 성공했습니다."),
    SERVER_ERROR(5000, "서버에 오류가 발생했습니다. \n 잠시 후 다시 시도해주세요"),
    ;

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
