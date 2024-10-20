package zaza.techblog.global.handler.response.type;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import zaza.techblog.global.common.code.ResponseCode;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponse {

    private final String timestamp = LocalDateTime.now().toString();
    private ResponseCode responseCode;
    private String detailMessage;

    @Builder
    protected BaseResponse(ResponseCode responseCode) {

        this.responseCode = responseCode;
        this.detailMessage = "";
    }

    @Builder
    protected BaseResponse(@NonNull final ResponseCode responseCode, final String detailMessage) {

        this.responseCode = responseCode;
        this.detailMessage = detailMessage;
    }

    public static BaseResponse ofSuccess() {

        return new BaseResponse(ResponseCode.SUCCESS);
    }

    public static BaseResponse ofError(ResponseCode responseCode) {

        return new BaseResponse(responseCode, "");
    }

    public static BaseResponse ofError(ResponseCode responseCode, String detailMessage) {

        return new BaseResponse(responseCode, detailMessage);
    }
}
