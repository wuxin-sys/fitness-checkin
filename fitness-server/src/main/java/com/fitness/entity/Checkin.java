package com.fitness.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 健身打卡实体类
 */
@Data
@TableName("checkin")
public class Checkin {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long userId;

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

    /** 创建时间 */
    private LocalDateTime createTime;

    // 以下为关联查询字段（非数据库字段）
    /** 用户名（关联查询用） */
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private String username;

    /** 昵称（关联查询用） */
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private String nickname;
}
