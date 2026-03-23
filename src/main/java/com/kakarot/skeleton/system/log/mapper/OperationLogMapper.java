package com.kakarot.skeleton.system.log.mapper;

import com.kakarot.skeleton.common.annotation.OperationLog;
import com.kakarot.skeleton.system.log.entity.OperationLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper {

    @Insert("""
            insert into sys_operation_log
            (module, operation, request_method, request_uri, operation_id, operation_name,
            request_param, response_result, status, error_msg, cost_time, operation_time)
            values
            (#{module}, #{operation}, #{requestMethod}, #{requestUri}, #{operationId}, #{operationName}, 
            #{requestParam}, #{responseResult}, #{status}, #{errorMsg}, #{costTime}, #{operationTime}
            )
            """)
    int insert(OperationLogEntity entity);

}
