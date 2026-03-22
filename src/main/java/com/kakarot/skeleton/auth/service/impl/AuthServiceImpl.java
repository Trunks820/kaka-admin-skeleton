package com.kakarot.skeleton.auth.service.impl;

import cn.hutool.json.JSONUtil;
import com.kakarot.skeleton.auth.dto.LoginRequest;
import com.kakarot.skeleton.auth.service.AuthService;
import com.kakarot.skeleton.auth.service.PermissionService;
import com.kakarot.skeleton.auth.service.RoleService;
import com.kakarot.skeleton.common.exception.BizException;
import com.kakarot.skeleton.common.model.LoginUser;
import com.kakarot.skeleton.common.util.JwtUtil;
import com.kakarot.skeleton.system.user.entity.User;
import com.kakarot.skeleton.system.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String login(LoginRequest request) {
        User user = userService.findByUserName(request.getUsername());

        if(user == null || Integer.valueOf(1).equals(user.getDelFlag())){
            throw new BizException(4001, "用户不存在！");
        }

        if(Integer.valueOf(0).equals(user.getStatus())){
            throw new BizException(4002, "用户已禁用！");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword().trim())){
           throw new BizException(4003, "密码错误");
        }

        //登录时装组装
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setNickName(user.getNickname());
        loginUser.setRoles(roleService.findRoleCodesByUserId(user.getId()));
        loginUser.setPermissions(permissionService.findPermCodesByUserId(user.getId()));

        String token = JwtUtil.generateToken(user.getId(), user.getUsername());
        String redisKey = "login:token:" + token;
        stringRedisTemplate.opsForValue().set(redisKey, JSONUtil.toJsonStr(loginUser),30, TimeUnit.MINUTES);
        return token;
    }


    @Override
    public void logout(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new BizException(401, "token不能为空");
        }
        stringRedisTemplate.delete("login:token:" + token);
    }
}
