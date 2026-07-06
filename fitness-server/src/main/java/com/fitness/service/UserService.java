package com.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fitness.dto.*;
import com.fitness.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /** 用户注册 */
    Result<?> register(RegisterDTO dto);

    /** 用户登录 */
    Result<?> login(LoginDTO dto);

    /** 修改密码 */
    Result<?> updatePassword(Long userId, PasswordDTO dto);

    /** 修改个人信息 */
    Result<?> updateProfile(Long userId, User user);

    /** 获取用户信息 */
    Result<?> getUserInfo(Long userId);
}
