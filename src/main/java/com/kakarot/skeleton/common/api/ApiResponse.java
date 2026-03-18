package com.kakarot.skeleton.common.api;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public ApiResponse(){

    }

    public ApiResponse(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(){
        return new ApiResponse<>(200, "success", null);
    }

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(200, "success", data);
    }

    public static <T> ApiResponse<T> success(String msg, T data){
        return new ApiResponse<>(200, msg, data);
    }

    public static <T> ApiResponse<T> fail(Integer code, String msg){
        return new ApiResponse<>(code, msg, null);
    }

    public static <T> ApiResponse<T> fail(String msg){
        return new ApiResponse<>(-1, msg, null);
    }

    public static <T> ApiResponse<T> fail(String msg, T data){
        return new ApiResponse<>(-1, msg, data);
    }



}
