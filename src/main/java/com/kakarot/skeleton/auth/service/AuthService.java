package com.kakarot.skeleton.auth.service;

import com.kakarot.skeleton.auth.dto.LoginRequest;

public interface AuthService {


    String login(LoginRequest request);

    void logout(String token);
}
