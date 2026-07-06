package com.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fitness.dto.*;
import com.fitness.entity.Admin;
import com.fitness.entity.Checkin;
import com.fitness.entity.User;
import com.fitness.mapper.AdminMapper;
import com.fitness.mapper.CheckinMapper;
import com.fitness.mapper.UserMapper;
import com.fitness.service.AdminService;
import com.fitness.utils.JwtUtil;
import com.fitness.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员服务实现
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CheckinMapper checkinMapper;

    /**
     * 管理员登录
     */
    @Override
    public Result<?> login(LoginDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        // 查询管理员
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, dto.getUsername());
        Admin admin = this.getOne(wrapper);

        if (admin == null) {
            return Result.error("用户名或密码错误");
        }

        // 校验密码
        if (!PasswordUtil.match(dto.getPassword(), admin.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        // 生成管理员 Token
        String token = jwtUtil.generateAdminToken(admin.getId(), admin.getUsername());

        admin.setPassword(null);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("admin", admin);

        return Result.ok("登录成功", resultMap);
    }

    /**
     * 数据概览
     */
    @Override
    public Result<AdminStatsDTO> getStats() {
        AdminStatsDTO stats = new AdminStatsDTO();

        // 用户总数
        stats.setUserCount(userMapper.selectCount(null));

        // 打卡总数
        stats.setCheckinCount(checkinMapper.selectCount(null));

        // 今日打卡人数
        stats.setTodayCheckinCount(checkinMapper.countTodayCheckinUsers(LocalDate.now()));

        return Result.ok(stats);
    }

    /**
     * 分页查询用户
     */
    @Override
    public Result<?> pageUsers(PageQueryDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        // 关键字搜索
        if (dto.getKeyword() != null && !dto.getKeyword().trim().isEmpty()) {
            wrapper.and(w -> w
                    .like(User::getUsername, dto.getKeyword())
                    .or()
                    .like(User::getNickname, dto.getKeyword())
            );
        }

        wrapper.orderByDesc(User::getCreateTime);

        Page<User> page = new Page<>(dto.getPage(), dto.getPageSize());
        Page<User> result = userMapper.selectPage(page, wrapper);

        // 隐藏密码
        for (User user : result.getRecords()) {
            user.setPassword(null);
        }

        return Result.ok(result);
    }

    /**
     * 管理员新增用户
     */
    @Override
    public Result<?> addUser(RegisterDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        // 检查用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            return Result.error("用户名已存在");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordUtil.encrypt(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setGender(dto.getGender() != null ? dto.getGender() : 0);
        user.setPhone(dto.getPhone());

        userMapper.insert(user);
        return Result.ok("用户添加成功");
    }

    /**
     * 管理员修改用户
     */
    @Override
    public Result<?> updateUser(Long userId, com.fitness.entity.User updateUser) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 更新允许修改的字段
        if (updateUser.getNickname() != null) {
            user.setNickname(updateUser.getNickname());
        }
        if (updateUser.getGender() != null) {
            user.setGender(updateUser.getGender());
        }
        if (updateUser.getPhone() != null) {
            user.setPhone(updateUser.getPhone());
        }
        // 管理员可以重置密码
        if (updateUser.getPassword() != null && !updateUser.getPassword().trim().isEmpty()) {
            user.setPassword(PasswordUtil.encrypt(updateUser.getPassword()));
        }

        userMapper.updateById(user);
        return Result.ok("用户修改成功");
    }

    /**
     * 管理员删除用户（级联删除打卡记录）
     */
    @Override
    @Transactional
    public Result<?> deleteUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 级联删除该用户的打卡记录
        LambdaQueryWrapper<Checkin> checkinWrapper = new LambdaQueryWrapper<>();
        checkinWrapper.eq(Checkin::getUserId, userId);
        checkinMapper.delete(checkinWrapper);

        // 删除用户
        userMapper.deleteById(userId);

        return Result.ok("用户删除成功");
    }
}
