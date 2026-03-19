package com.kakarot.skeleton.auth.vo;

import lombok.Data;

@Data
public class CurrentUserResponse {
    private Long id;
    private String username;
    private String nickname;
}
