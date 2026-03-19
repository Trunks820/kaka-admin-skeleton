package com.kakarot.skeleton.common.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class LoginUser {

    private Long userId;
    private String username;
    private String nickName;

    private Set<String> roles = new HashSet<>();
    private Set<String> permissions = new HashSet<>();

    public boolean hasRole(String roleCode){
        return roles != null && roles.contains(roleCode);
    }

    public boolean hasPermission(String permission){
        return permissions != null && permissions.contains(permission);
    }

}
