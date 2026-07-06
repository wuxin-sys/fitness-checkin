package com.fitness.dto;

import lombok.Data;

/**
 * 分页查询请求参数
 */
@Data
public class PageQueryDTO {

    /** 当前页码，默认第1页 */
    private Integer page = 1;

    /** 每页条数，默认10条 */
    private Integer pageSize = 10;

    /** 搜索关键字 */
    private String keyword;
}
