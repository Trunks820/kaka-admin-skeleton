package com.kakarot.skeleton.auth.service.impl;

import com.kakarot.skeleton.auth.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Override
    public Set<String> findPermCodesByUserId(Long userId) {
        return null;
    }
}
