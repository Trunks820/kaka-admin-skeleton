package com.kakarot.skeleton.system.log.service.impl;

import com.kakarot.skeleton.system.log.entity.OperationLogEntity;
import com.kakarot.skeleton.system.log.mapper.OperationLogMapper;
import com.kakarot.skeleton.system.log.service.OperationLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public void save(OperationLogEntity log) {
        operationLogMapper.insert(log);
    }
}
