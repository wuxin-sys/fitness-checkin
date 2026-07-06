package com.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fitness.dto.*;
import com.fitness.entity.User;
import com.fitness.mapper.UserMapper;
import com.fitness.service.UserService;
import com.fitness.utils.JwtUtil;
import com.fitness.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @Override
    public Result<?> register(RegisterDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        // 检查用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        if (this.count(wrapper) > 0) {
            return Result.error("用户名已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordUtil.encrypt(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setGender(dto.getGender() != null ? dto.getGender() : 0);
        user.setPhone(dto.getPhone());

        this.save(user);
        return Result.ok("注册成功");
    }

    /**
     * 用户登录
     */
    @Override
    public Result<?> login(LoginDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        // 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = this.getOne(wrapper);

        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        // 校验密码
        if (!PasswordUtil.match(dto.getPassword(), user.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        // 生成 Token
        String token = jwtUtil.generateUserToken(user.getId(), user.getUsername());

        // 返回 Token 和用户信息（不返回密码）
        user.setPassword(null);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("user", user);

        return Result.ok("登录成功", resultMap);
    }

    /**
     * 修改密码
     */
    @Override
    public Result<?> updatePassword(Long userId, PasswordDTO dto) {
        if (dto.getOldPassword() == null || dto.getNewPassword() == null) {
            return Result.error("旧密码和新密码不能为空");
        }

        User user = this.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 校验旧密码
        if (!PasswordUtil.match(dto.getOldPassword(), user.getPassword())) {
            return Result.error("旧密码错误");
        }

        // 更新密码
        user.setPassword(PasswordUtil.encrypt(dto.getNewPassword()));
        this.updateById(user);

        return Result.ok("密码修改成功");
    }

    /**
     * 修改个人信息
     */
    @Override
    public Result<?> updateProfile(Long userId, User updateUser) {
        User user = this.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 只更新允许修改的字段
        if (updateUser.getNickname() != null) {
            user.setNickname(updateUser.getNickname());
        }
        if (updateUser.getGender() != null) {
            user.setGender(updateUser.getGender());
        }
        if (updateUser.getPhone() != null) {
            user.setPhone(updateUser.getPhone());
        }

        this.updateById(user);

        user.setPassword(null);
        return Result.ok("个人信息修改成功", user);
    }

    /**
     * 获取用户信息
     */
    @Override
    public Result<?> getUserInfo(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setPassword(null);
        return Result.ok(user);
    }
}
