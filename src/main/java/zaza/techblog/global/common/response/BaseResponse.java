package zaza.techblog.global.common.response;

import lombok.Getter;
import lombok.Setter;
import zaza.techblog.global.common.code.ResponseCode;

@Getter
@Setter
public class BaseResponse {

    private ResponseCode responseCode;

    public BaseResponse(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
