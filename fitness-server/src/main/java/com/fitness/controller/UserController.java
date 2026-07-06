package com.fitness.controller;

import com.fitness.dto.*;
import com.fitness.entity.User;
import com.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 * 处理用户注册、登录、个人信息管理
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * POST /api/user/register
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    /**
     * 用户登录
     * POST /api/user/login
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }

    /**
     * 获取当前登录用户信息
     * GET /api/user/info
     */
    @GetMapping("/info")
    public Result<?> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return userService.getUserInfo(userId);
    }

    /**
     * 修改密码
     * PUT /api/user/password
     */
    @PutMapping("/password")
    public Result<?> updatePassword(HttpServletRequest request, @RequestBody PasswordDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        return userService.updatePassword(userId, dto);
    }

    /**
     * 修改个人信息
     * PUT /api/user/profile
     */
    @PutMapping("/profile")
    public Result<?> updateProfile(HttpServletRequest request, @RequestBody User user) {
        Long userId = (Long) request.getAttribute("userId");
        return userService.updateProfile(userId, user);
    }
}
