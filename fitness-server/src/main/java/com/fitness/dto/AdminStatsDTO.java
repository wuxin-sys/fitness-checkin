package com.fitness.dto;

import lombok.Data;

/**
 * 管理员统计数据
 */
@Data
public class AdminStatsDTO {

    /** 用户总数 */
    private Long userCount;

    /** 打卡总数 */
    private Long checkinCount;

    /** 今日打卡人数 */
    private Long todayCheckinCount;
}
