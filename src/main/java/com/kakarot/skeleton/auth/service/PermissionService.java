package com.kakarot.skeleton.auth.service;

import java.util.Set;

public interface PermissionService {

    Set<String> findPermCodesByUserId(Long userId);

}
