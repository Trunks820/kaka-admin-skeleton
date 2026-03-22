package com.kakarot.skeleton.auth.service;

import java.util.Set;

public interface RoleService {

    Set<String> findRoleCodesByUserId(Long userId);
}
