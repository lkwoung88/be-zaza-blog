package zaza.techblog.global.handler.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import zaza.techblog.global.common.response.BaseResponse;

@RestControllerAdvice
public class BaseResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType) &&
                (returnType.getMethod().getReturnType().equals(void.class) || returnType.getMethod().getReturnType().equals(BaseResponse.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(returnType.getMethod().getReturnType().equals(void.class)) {

            return ResponseEntity.ok(BaseResponse.ofSuccess());
        }

        return ResponseEntity.ok(body);
    }
}
