package com.kakarot.skeleton.controller;

import com.kakarot.skeleton.common.api.ApiResponse;
import com.kakarot.skeleton.common.exception.BizException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/test/ping")
    public ApiResponse<String> ping(){
        return ApiResponse.success("pong");
    }

    @GetMapping("/test/biz-error")
    public ApiResponse<Void> bizError() {
        throw new BizException(4001, "这是一个业务异常测试");
    }


}
