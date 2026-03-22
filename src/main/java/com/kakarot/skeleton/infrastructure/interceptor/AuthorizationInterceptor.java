package com.kakarot.skeleton.infrastructure.interceptor;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.kakarot.skeleton.common.context.UserContext;
import com.kakarot.skeleton.common.exception.ForbiddenException;
import com.kakarot.skeleton.common.model.LoginUser;
import com.kakarot.skeleton.infrastructure.annotation.RequirePermission;
import com.kakarot.skeleton.infrastructure.annotation.RequireRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }

        LoginUser loginUser = UserContext.get();
        if(loginUser == null){
            return true;
        }

        checkRole(handlerMethod, loginUser);
        checkPermission(handlerMethod, loginUser);
        return true;
    }

    private void checkRole(HandlerMethod handlerMethod, LoginUser loginUser){
        RequireRole requireRole = getAnnotation(handlerMethod, RequireRole.class);
        if(requireRole == null){
            return;
        }

        Set<String> roles = loginUser.getRoles() == null ? Collections.emptySet() : loginUser.getRoles();
        boolean match = Arrays.stream(requireRole.value()).anyMatch(roles::contains);
        if(!match){
            throw new ForbiddenException("无角色权限， 禁止访问");
        }

    }

    private void checkPermission(HandlerMethod handlerMethod, LoginUser loginUser){
        RequirePermission requirePermission = getAnnotation(handlerMethod, RequirePermission.class);
        if(requirePermission == null){
            return;
        }

        Set<String> permissions = loginUser.getPermissions() == null
                ? Collections.emptySet() : loginUser.getPermissions();

        boolean match = Arrays.stream(requirePermission.value()).anyMatch(permissions::contains);
        if(!match){
            throw new ForbiddenException("无操作权限， 禁止访问");
        }
    }

    private <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationClass){
        A methodAnnotation = handlerMethod.getMethodAnnotation(annotationClass);
        if(methodAnnotation != null){
            return methodAnnotation;
        }

        return handlerMethod.getBeanType().getAnnotation(annotationClass);
    }

}
