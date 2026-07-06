package com.fitness.controller;

import com.fitness.dto.CheckinDTO;
import com.fitness.dto.PageQueryDTO;
import com.fitness.dto.Result;
import com.fitness.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 健身打卡控制器
 */
@RestController
@RequestMapping("/api/checkin")
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    /**
     * 新增打卡
     * POST /api/checkin
     */
    @PostMapping
    public Result<?> addCheckin(HttpServletRequest request, @RequestBody CheckinDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.addCheckin(userId, dto);
    }

    /**
     * 修改打卡
     * PUT /api/checkin/{id}
     */
    @PutMapping("/{id}")
    public Result<?> updateCheckin(HttpServletRequest request,
                                   @PathVariable Long id,
                                   @RequestBody CheckinDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.updateCheckin(userId, id, dto);
    }

    /**
     * 删除打卡
     * DELETE /api/checkin/{id}
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteCheckin(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.deleteCheckin(userId, id);
    }

    /**
     * 分页查询个人打卡记录
     * GET /api/checkin/page
     */
    @GetMapping("/page")
    public Result<?> pageMyCheckins(HttpServletRequest request, PageQueryDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.pageMyCheckins(userId, dto);
    }

    /**
     * 查询今日是否打卡
     * GET /api/checkin/today
     */
    @GetMapping("/today")
    public Result<?> getTodayStatus(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.getTodayStatus(userId);
    }

    /**
     * 连续打卡天数
     * GET /api/checkin/streak
     */
    @GetMapping("/streak")
    public Result<?> getStreak(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.getStreak(userId);
    }

    /**
     * 本周打卡次数
     * GET /api/checkin/week-count
     */
    @GetMapping("/week-count")
    public Result<?> getWeekCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.getWeekCount(userId);
    }

    /**
     * 本月打卡次数
     * GET /api/checkin/stats/month
     */
    @GetMapping("/stats/month")
    public Result<?> getMonthCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.getMonthCount(userId);
    }

    /**
     * 每月打卡趋势（近6个月）
     * GET /api/checkin/stats/monthly
     */
    @GetMapping("/stats/monthly")
    public Result<?> getMonthlyStats(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.getMonthlyStats(userId);
    }

    /**
     * 训练类型占比
     * GET /api/checkin/stats/type
     */
    @GetMapping("/stats/type")
    public Result<?> getTypeStats(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return checkinService.getTypeStats(userId);
    }
}
