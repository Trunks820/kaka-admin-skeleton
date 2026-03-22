package com.kakarot.skeleton.auth.service.impl;

import com.kakarot.skeleton.auth.service.RoleService;
import com.kakarot.skeleton.system.user.mapper.RoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {


    @Resource
    private RoleMapper roleMapper;

    @Override
    public Set<String> findRoleCodesByUserId(Long userId) {
        return roleMapper.findRoleCodesByUserId(userId);
    }
}
