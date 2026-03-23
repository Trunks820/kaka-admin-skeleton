package com.kakarot.skeleton.system.log.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLogEntity {

    private Long id;
    private String module;
    private String operation;
    private String requestMethod;
    private String requestUri;
    private Long operationId;
    private String operationName;
    private String requestParam;
    private String responseResult;
    private Integer status;
    private String errorMsg;
    private Long  costTime;
    private LocalDateTime operationTime;

}
