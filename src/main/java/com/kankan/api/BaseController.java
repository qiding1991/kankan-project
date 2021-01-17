package com.kankan.api;

import static com.kankan.constant.ErrorCode.PARAM_CHECK_ERROR;

import com.kankan.constant.CommonResponse;
import com.kankan.constant.ErrorCode;

import lombok.extern.log4j.Log4j2;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class BaseController {

    /**
     * 未知异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public CommonResponse exception(Throwable throwable) {
        log.error("服务器未知异常", throwable);
        return CommonResponse.error(ErrorCode.UN_KNOW_ERROR,throwable.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = "参数校验错误";
        if (fieldError != null) {
            message = fieldError.getDefaultMessage();
        }
        return CommonResponse.error(PARAM_CHECK_ERROR, message);
    }

    /**
     * 成功
     */
    public <T> CommonResponse success(T result) {
        return CommonResponse.success(result);
    }


    /**
     * 成功
     */
    public <T> CommonResponse success() {
        return CommonResponse.success();
    }
}
