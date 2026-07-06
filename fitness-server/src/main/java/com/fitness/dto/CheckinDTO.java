package com.fitness.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 打卡请求参数
 */
@Data
public class CheckinDTO {

    /** 打卡日期 */
    private LocalDate checkinDate;

    /** 训练类型 */
    private String sportType;

    /** 训练时长（分钟） */
    private Integer duration;

    /** 消耗热量（千卡） */
    private Integer calorie;

    /** 备注 */
    private String remark;
}
