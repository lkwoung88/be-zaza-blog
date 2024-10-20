package zaza.techblog.global.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.DuplicateMappingException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zaza.techblog.global.common.code.ResponseCode;
import zaza.techblog.global.handler.response.type.BaseResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(Exception exception) {

        log.error(exception.getMessage(), exception);

        return BaseResponse.ofError(ResponseCode.SERVER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse bindExceptionHandler(BindException exception) {

        log.error(exception.getMessage(), exception);
        String detailMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return BaseResponse.ofError(ResponseCode.INPUT_ERROR, detailMessage);
    }

    @ExceptionHandler(DuplicateMappingException.class)
    public BaseResponse duplicateMappingExceptionHandler(DuplicateMappingException exception) {

        log.error(exception.getMessage(), exception);

        return BaseResponse.ofError(ResponseCode.CONFLICT_ERROR);
    }
}
