package com.kakarot.skeleton.auth.service.impl;

import com.kakarot.skeleton.auth.service.PermissionService;
import com.kakarot.skeleton.system.user.mapper.PermissionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {


    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> findPermCodesByUserId(Long userId) {
        return permissionMapper.findPermCodesByUserId(userId);
    }
}
