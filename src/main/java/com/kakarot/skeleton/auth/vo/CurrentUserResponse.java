package com.kakarot.skeleton.auth.vo;

import lombok.Data;

import java.util.Set;

@Data
public class CurrentUserResponse {
    private Long id;
    private String username;
    private String nickname;
    private Set<String> roles;
    private Set<String> permissions;
}
