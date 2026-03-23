package com.kakarot.skeleton.common.aspect;

import cn.hutool.json.JSONUtil;
import com.kakarot.skeleton.common.annotation.OperationLog;
import com.kakarot.skeleton.common.context.UserContext;
import com.kakarot.skeleton.common.model.LoginUser;
import com.kakarot.skeleton.system.log.entity.OperationLogEntity;
import com.kakarot.skeleton.system.log.service.OperationLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class OperationLogAspect {

    private static final int MAX_PARAM_LENGTH = 3000;
    private static final int MAX_RESULT_LENGTH = 3000;
    private static final int MAX_ERROR_LENGTH = 1000;


    @Resource
    private OperationLogService operationLogService;

    @Around("@annotation(operationLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperationLog operationLog)throws  Throwable{
        long start = System.currentTimeMillis();

        OperationLogEntity log = new OperationLogEntity();
        log.setModule(operationLog.module());
        log.setOperation(operationLog.operation());
        log.setOperationTime(LocalDateTime.now());

        HttpServletRequest request = getRequest();
        if(request != null){
            log.setRequestMethod(request.getMethod());
            log.setRequestUri(request.getRequestURI());
        }

        log.setRequestParam(buildRequestParam(joinPoint.getArgs()));

        Object result = null;
        Throwable bizException = null;

        LoginUser loginUser = UserContext.get();
        if(loginUser != null){
           log.setOperationId(loginUser.getUserId());
           log.setOperationName(loginUser.getUsername());
        }

        try {
            result = joinPoint.proceed();
            log.setStatus(1);
            log.setResponseResult(toJsonSafely(result));
            return result;
        }catch (Throwable e){
            log.setStatus(0);
            log.setErrorMsg(truncate(e.getClass().getSimpleName() + ": " + e.getMessage(), MAX_ERROR_LENGTH));
            throw e;
        }finally {
            log.setCostTime(System.currentTimeMillis() - start);

            // 失败时也尽量把返回结果置空，避免脏数据
            if (bizException != null) {
                log.setResponseResult(null);
            }

            // 日志落库不能影响主流程
            try {
                operationLogService.save(log);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            return null;
        }
        return servletRequestAttributes.getRequest();
    }

    private String buildRequestParam(Object[] args){
        if(args == null || args.length == 0){
            return null;
        }

        List<Object> validArgs = new ArrayList<>();
        for (Object arg : args) {
            if(shouldIgnore(arg)){
                continue;
            }
            validArgs.add(arg);
        }

        if (validArgs.isEmpty()) {
            return null;
        }

        return truncate(toJsonSafely(validArgs), MAX_PARAM_LENGTH);
    }


    private boolean shouldIgnore(Object arg){
        if(arg == null){
            return false;
        }

        return arg instanceof ServletRequest
                || arg instanceof ServletResponse
                || arg instanceof MultipartFile
                || arg instanceof BindingResult;
    }

    private String toJsonSafely(Object obj){
        if(obj == null){
            return null;
        }

        try {
            return JSONUtil.toJsonStr(obj);
        }catch (Exception e){
            return String.valueOf(obj);
        }

    }

    private String truncate(String str, int maxLen){
        if(str == null || str.length() <= maxLen){
            return str;
        }
        return str.substring(0, maxLen) + "...";
    }



}
