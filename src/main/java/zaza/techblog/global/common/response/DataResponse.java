package zaza.techblog.global.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import zaza.techblog.global.common.code.ResponseCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataResponse<T> extends BaseResponse {

    T data;

    protected DataResponse(ResponseCode responseCode, T data) {
        super(responseCode);
        this.data = data;
    }

    public static <T> DataResponse<T> ofSuccess(@Nullable T data) {
        return new DataResponse<>(ResponseCode.SUCCESS, data);
    }
}
