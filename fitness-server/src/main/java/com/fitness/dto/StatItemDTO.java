package com.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计数据项（月度趋势 / 类型占比通用）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatItemDTO {

    /** 统计维度名称（月份 / 训练类型） */
    private String name;

    /** 统计数值 */
    private Long value;
}
