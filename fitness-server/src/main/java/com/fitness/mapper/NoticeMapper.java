package com.fitness.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fitness.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公告 Mapper
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
