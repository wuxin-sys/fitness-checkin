package com.fitness.controller;

import com.fitness.dto.*;
import com.fitness.entity.Notice;
import com.fitness.entity.User;
import com.fitness.service.AdminService;
import com.fitness.service.CheckinService;
import com.fitness.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制器
 * 处理管理员登录、后台管理相关接口
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private NoticeService noticeService;

    // ========== 管理员登录 ==========

    /**
     * 管理员登录
     * POST /api/admin/login
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO dto) {
        return adminService.login(dto);
    }

    // ========== 数据概览 ==========

    /**
     * 后台首页数据概览
     * GET /api/admin/stats
     */
    @GetMapping("/stats")
    public Result<AdminStatsDTO> getStats() {
        return adminService.getStats();
    }

    // ========== 用户管理 ==========

    /**
     * 分页查询用户
     * GET /api/admin/user/page
     */
    @GetMapping("/user/page")
    public Result<?> pageUsers(PageQueryDTO dto) {
        return adminService.pageUsers(dto);
    }

    /**
     * 新增用户
     * POST /api/admin/user
     */
    @PostMapping("/user")
    public Result<?> addUser(@RequestBody RegisterDTO dto) {
        return adminService.addUser(dto);
    }

    /**
     * 修改用户
     * PUT /api/admin/user/{id}
     */
    @PutMapping("/user/{id}")
    public Result<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        return adminService.updateUser(id, user);
    }

    /**
     * 删除用户
     * DELETE /api/admin/user/{id}
     */
    @DeleteMapping("/user/{id}")
    public Result<?> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    // ========== 打卡管理 ==========

    /**
     * 分页查询所有打卡记录
     * GET /api/admin/checkin/page
     */
    @GetMapping("/checkin/page")
    public Result<?> pageAllCheckins(PageQueryDTO dto) {
        return checkinService.pageAllCheckins(dto);
    }

    /**
     * 删除打卡记录
     * DELETE /api/admin/checkin/{id}
     */
    @DeleteMapping("/checkin/{id}")
    public Result<?> deleteCheckin(@PathVariable Long id) {
        return checkinService.adminDeleteCheckin(id);
    }

    // ========== 公告管理 ==========

    /**
     * 分页查询公告列表
     * GET /api/admin/notice/page
     */
    @GetMapping("/notice/page")
    public Result<?> pageNotices(PageQueryDTO dto) {
        return noticeService.pageNotices(dto);
    }

    /**
     * 发布公告
     * POST /api/admin/notice
     */
    @PostMapping("/notice")
    public Result<?> addNotice(@RequestBody Notice notice) {
        return noticeService.addNotice(notice);
    }

    /**
     * 修改公告
     * PUT /api/admin/notice/{id}
     */
    @PutMapping("/notice/{id}")
    public Result<?> updateNotice(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        return noticeService.updateNotice(notice);
    }

    /**
     * 删除公告
     * DELETE /api/admin/notice/{id}
     */
    @DeleteMapping("/notice/{id}")
    public Result<?> deleteNotice(@PathVariable Long id) {
        return noticeService.deleteNotice(id);
    }
}
