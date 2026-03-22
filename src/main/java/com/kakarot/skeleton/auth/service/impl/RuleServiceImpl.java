package com.kakarot.skeleton.auth.service.impl;

import com.kakarot.skeleton.auth.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RuleServiceImpl implements RuleService {

    @Override
    public Set<String> findRoleCodesByUserId(Long userId) {
        return null;
    }
}
