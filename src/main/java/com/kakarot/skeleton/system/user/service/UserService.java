package com.kakarot.skeleton.system.user.service;

import com.kakarot.skeleton.system.user.entity.User;

public interface UserService {

    User findByUserName(String username);

}
