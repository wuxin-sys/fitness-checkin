package com.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fitness.dto.*;
import com.fitness.entity.Checkin;

/**
 * 健身打卡服务接口
 */
public interface CheckinService extends IService<Checkin> {

    /** 新增打卡 */
    Result<?> addCheckin(Long userId, CheckinDTO dto);

    /** 修改打卡 */
    Result<?> updateCheckin(Long userId, Long checkinId, CheckinDTO dto);

    /** 删除打卡 */
    Result<?> deleteCheckin(Long userId, Long checkinId);

    /** 分页查询个人打卡记录 */
    Result<?> pageMyCheckins(Long userId, PageQueryDTO dto);

    /** 查询今日是否打卡 */
    Result<?> getTodayStatus(Long userId);

    /** 连续打卡天数 */
    Result<?> getStreak(Long userId);

    /** 本周打卡次数 */
    Result<?> getWeekCount(Long userId);

    /** 本月打卡次数 */
    Result<?> getMonthCount(Long userId);

    /** 每月打卡趋势（近6个月） */
    Result<?> getMonthlyStats(Long userId);

    /** 训练类型占比 */
    Result<?> getTypeStats(Long userId);

    // ========== 管理员接口 ==========

    /** 分页查询所有打卡记录 */
    Result<?> pageAllCheckins(PageQueryDTO dto);

    /** 管理员删除打卡 */
    Result<?> adminDeleteCheckin(Long checkinId);
}
