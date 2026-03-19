package com.kakarot.skeleton.system.user.service.impl;

import com.kakarot.skeleton.system.user.entity.User;
import com.kakarot.skeleton.system.user.mapper.UserMapper;
import com.kakarot.skeleton.system.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
