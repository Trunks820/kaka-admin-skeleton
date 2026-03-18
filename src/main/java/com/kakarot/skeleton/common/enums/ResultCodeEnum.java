package com.kakarot.skeleton.common.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "success"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未登录或登录已失效"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    SYSTEM_ERROR(500, "系统异常");

    private final Integer code;
    private final String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
