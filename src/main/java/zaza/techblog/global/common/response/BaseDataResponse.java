package zaza.techblog.global.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import zaza.techblog.global.common.code.ResponseCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseDataResponse<T> extends BaseResponse {

    T data;

    protected BaseDataResponse(ResponseCode responseCode, T data) {
        super(responseCode);
        this.data = data;
    }

    public static <T> BaseDataResponse<T> ofSuccess(@Nullable T data) {
        return new BaseDataResponse<>(ResponseCode.SUCCESS, data);
    }
}
