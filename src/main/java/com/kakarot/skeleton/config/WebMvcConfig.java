package com.kakarot.skeleton.config;

import com.kakarot.skeleton.infrastructure.interceptor.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    List<String> patterns = new ArrayList<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        patterns.add("/auth/login");
        patterns.add("/auth/logout");
        patterns.add("/test/**");
        patterns.add("/error");
        patterns.add("/favicon.ico");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
