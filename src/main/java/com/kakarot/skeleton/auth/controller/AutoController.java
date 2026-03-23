package com.kakarot.skeleton.auth.controller;

import com.kakarot.skeleton.auth.dto.LoginRequest;
import com.kakarot.skeleton.auth.service.AuthService;
import com.kakarot.skeleton.auth.service.PermissionService;
import com.kakarot.skeleton.auth.service.RoleService;
import com.kakarot.skeleton.auth.vo.CurrentUserResponse;
import com.kakarot.skeleton.common.api.ApiResponse;
import com.kakarot.skeleton.common.context.UserContext;
import com.kakarot.skeleton.common.exception.BizException;
import com.kakarot.skeleton.common.model.LoginUser;
import com.kakarot.skeleton.system.user.entity.User;
import com.kakarot.skeleton.system.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AutoController {

    @Resource
    private AuthService authService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;


    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid LoginRequest request){
        String token = authService.login(request);
        return ApiResponse.success(token);
    }

    @GetMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("token") String token){
        authService.logout(token);
        return ApiResponse.success();
    }


    @GetMapping("/me")
    public ApiResponse<CurrentUserResponse> authMe(){
        LoginUser loginUser = UserContext.get();
        if (loginUser == null) {
            throw new BizException(401, "当前用户未登录");
        }

        User user = userService.findByUserName(loginUser.getUsername());
        if (loginUser == null) {
            throw new BizException(401, "当前用户未登录");
        }

        Set<String> roleCodesByUserId =
                roleService.findRoleCodesByUserId(user.getId());

        Set<String> permCodesByUserId =
                permissionService.findPermCodesByUserId(user.getId());

        CurrentUserResponse currentUserResponse = new CurrentUserResponse();
        currentUserResponse.setId(user.getId());
        currentUserResponse.setUsername(user.getUsername());
        currentUserResponse.setNickname(user.getNickname());
        currentUserResponse.setRoles(roleCodesByUserId);
        currentUserResponse.setPermissions(permCodesByUserId);
        return ApiResponse.success(currentUserResponse);
    }


}
