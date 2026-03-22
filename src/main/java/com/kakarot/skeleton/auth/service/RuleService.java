package com.kakarot.skeleton.auth.service;

import java.util.Set;

public interface RuleService {


    Set<String> findRoleCodesByUserId(Long userId);

}
