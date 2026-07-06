package com.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fitness.dto.PageQueryDTO;
import com.fitness.dto.Result;
import com.fitness.entity.Notice;
import com.fitness.mapper.NoticeMapper;
import com.fitness.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * 公告服务实现
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    /**
     * 分页查询公告列表（按创建时间倒序）
     */
    @Override
    public Result<?> pageNotices(PageQueryDTO dto) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Notice::getCreateTime);

        Page<Notice> page = new Page<>(dto.getPage(), dto.getPageSize());
        Page<Notice> result = this.page(page, wrapper);

        return Result.ok(result);
    }

    /**
     * 查询公告详情
     */
    @Override
    public Result<?> getNoticeById(Long id) {
        Notice notice = this.getById(id);
        if (notice == null) {
            return Result.error("公告不存在");
        }
        return Result.ok(notice);
    }

    /**
     * 查询最新公告
     */
    @Override
    public Result<?> getLatestNotice() {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Notice::getCreateTime)
                .last("LIMIT 1");

        Notice notice = this.getOne(wrapper);
        return Result.ok(notice);
    }

    /**
     * 新增公告
     */
    @Override
    public Result<?> addNotice(Notice notice) {
        if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
            return Result.error("公告标题不能为空");
        }
        if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
            return Result.error("公告内容不能为空");
        }

        this.save(notice);
        return Result.ok("公告发布成功");
    }

    /**
     * 修改公告
     */
    @Override
    public Result<?> updateNotice(Notice notice) {
        Notice existing = this.getById(notice.getId());
        if (existing == null) {
            return Result.error("公告不存在");
        }

        existing.setTitle(notice.getTitle());
        existing.setContent(notice.getContent());
        this.updateById(existing);

        return Result.ok("公告修改成功");
    }

    /**
     * 删除公告
     */
    @Override
    public Result<?> deleteNotice(Long id) {
        Notice existing = this.getById(id);
        if (existing == null) {
            return Result.error("公告不存在");
        }

        this.removeById(id);
        return Result.ok("公告删除成功");
    }
}
