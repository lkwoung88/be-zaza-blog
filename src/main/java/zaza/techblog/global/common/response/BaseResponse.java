package zaza.techblog.global.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import zaza.techblog.global.common.code.ResponseCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponse {

    private ResponseCode responseCode;

    protected BaseResponse(@NonNull final ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public static BaseResponse ofSuccess() {
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    public static BaseResponse ofError(ResponseCode responseCode) {
        return new BaseResponse(responseCode);
    }
}
