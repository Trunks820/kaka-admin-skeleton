package com.kakarot.skeleton.auth.service.impl;

import com.kakarot.skeleton.auth.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public Set<String> findRoleCodesByUserId(Long userId) {
        return null;
    }
}
