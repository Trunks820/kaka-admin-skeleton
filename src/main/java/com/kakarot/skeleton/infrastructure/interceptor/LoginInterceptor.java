package com.kakarot.skeleton.infrastructure.interceptor;

import cn.hutool.json.JSONUtil;
import com.kakarot.skeleton.common.context.UserContext;
import com.kakarot.skeleton.common.exception.BizException;
import com.kakarot.skeleton.common.model.LoginUser;
import com.kakarot.skeleton.common.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        return check(request);
    }



    public Boolean check(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null || token.trim().isEmpty()){
            throw new BizException(4001, "当前用户未登录！");
        }

        Boolean tokenExpired = JwtUtil.isTokenExpired(token);
        if(tokenExpired){
            throw new BizException(4001, "当前登录已过期！");
        }

        String redisValue = stringRedisTemplate.opsForValue().get("login:token:" + token);
        if(redisValue == null || redisValue.trim().isEmpty()){
            throw new BizException(4001, "当前用户未登录！");
        }

        LoginUser loginUser = JSONUtil.toBean(redisValue, LoginUser.class);
        UserContext.set(loginUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}
