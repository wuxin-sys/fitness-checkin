package com.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fitness.dto.PageQueryDTO;
import com.fitness.dto.Result;
import com.fitness.entity.Notice;

/**
 * 公告服务接口
 */
public interface NoticeService extends IService<Notice> {

    /** 分页查询公告列表 */
    Result<?> pageNotices(PageQueryDTO dto);

    /** 查询公告详情 */
    Result<?> getNoticeById(Long id);

    /** 查询最新公告 */
    Result<?> getLatestNotice();

    /** 新增公告 */
    Result<?> addNotice(Notice notice);

    /** 修改公告 */
    Result<?> updateNotice(Notice notice);

    /** 删除公告 */
    Result<?> deleteNotice(Long id);
}
