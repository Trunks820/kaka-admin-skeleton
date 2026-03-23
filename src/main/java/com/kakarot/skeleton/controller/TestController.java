package com.kakarot.skeleton.controller;

import com.kakarot.skeleton.common.annotation.OperationLog;
import com.kakarot.skeleton.common.api.ApiResponse;
import com.kakarot.skeleton.common.exception.BizException;
import com.kakarot.skeleton.common.util.JwtUtil;
import com.kakarot.skeleton.infrastructure.annotation.RequirePermission;
import com.kakarot.skeleton.system.user.entity.User;
import com.kakarot.skeleton.system.user.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/test/ping")
    public ApiResponse<String> ping(){
        return ApiResponse.success("pong");
    }

    @GetMapping("/test/biz-error")
    public ApiResponse<Void> bizError() {
        throw new BizException(4001, "这是一个业务异常测试");
    }

    @GetMapping("/test/user")
    public ApiResponse<User> testUser(@RequestParam String username) {
        return ApiResponse.success(userMapper.findByUserName(username));
    }

    @GetMapping("/test/redis")
    public ApiResponse<String> testRedis() {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwiaWF0IjoxNzczOTAxNzM1LCJleHAiOjE3NzM5MDUzMzV9.xkd7RfkNSGmp4t4TQC-ltcvricU-72kIWpYz3w1WlVGFnTSeAvSROU-k5HAJtGGD";
        String s = stringRedisTemplate.opsForValue().get("login:token:" + token);
        return ApiResponse.success(s);
    }

    @GetMapping("/test/public")
    public ApiResponse<String> publicApi() {
        return ApiResponse.success("public ok");
    }

    @GetMapping("/test/user/add")
    @RequirePermission("user:add")
    public ApiResponse<String> userAdd() {
        return ApiResponse.success("add ok");
    }


    @GetMapping("/test/user/list")
    @RequirePermission("user:list")
    public ApiResponse<List<String>> userList(){
        return ApiResponse.success(Arrays.asList("admin", "testUser"));
    }

    @OperationLog(module = "日志测试", operation = "成功接口")
    @GetMapping("/log/success")
    public ApiResponse<String> success() {
        return ApiResponse.success("success");
    }

    @OperationLog(module = "日志测试", operation = "失败接口")
    @GetMapping("/log/fail")
    public ApiResponse<String> fail() {
        throw new BizException(5001, "测试失败");
    }


    public static void main(String[] args) {
//        String token = JwtUtil.generateToken(1L, "admin");
//        System.err.println(token);

//        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsInVzZXJJZCI6MSwiaWF0IjoxNzczODk1NTc1LCJleHAiOjE3NzM4OTkxNzV9.rP-PfKcx8E9NW2m01CbiouaaxzENEUQ8Tf5nDXMQHEneTWRJubPuuEnpYbAZXLQI";
//        Claims claims = JwtUtil.parseToken(token);
//        System.err.println(claims);
//
//        Boolean tokenExpired = JwtUtil.isTokenExpired(token);
//        System.err.println(tokenExpired);

    }
}
