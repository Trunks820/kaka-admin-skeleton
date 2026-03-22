package com.kakarot.skeleton.common.exception;

import com.kakarot.skeleton.common.api.ApiResponse;
import com.kakarot.skeleton.common.enums.ResultCodeEnum;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBizException(BizException e) {
        return ApiResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String msg = e.getBindingResult().getFieldError() != null ? e.getBindingResult().getFieldError().getDefaultMessage() : ResultCodeEnum.PARAM_ERROR.getMsg();
        return ApiResponse.fail(ResultCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse<Void> handleBindException(BindException e) {
        String msg = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : ResultCodeEnum.PARAM_ERROR.getMsg();
        return ApiResponse.fail(ResultCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
        return ApiResponse.fail(ResultCodeEnum.PARAM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        e.printStackTrace();
        return ApiResponse.fail(ResultCodeEnum.SYSTEM_ERROR.getCode(), ResultCodeEnum.SYSTEM_ERROR.getMsg());
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleHttpMessageNotReadableException(Exception e) {
        return ApiResponse.fail(400, "请求体JSON格式错误");
    }

    @ExceptionHandler(ForbiddenException.class)
    public ApiResponse<Void> handleForbidden(ForbiddenException e){
        return ApiResponse.fail(403, e.getMessage());
    }

}
