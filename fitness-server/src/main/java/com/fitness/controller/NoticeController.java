package com.fitness.controller;

import com.fitness.dto.PageQueryDTO;
import com.fitness.dto.Result;
import com.fitness.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告控制器（用户端）
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页查询公告列表
     * GET /api/notice/page
     */
    @GetMapping("/page")
    public Result<?> pageNotices(PageQueryDTO dto) {
        return noticeService.pageNotices(dto);
    }

    /**
     * 查询公告详情
     * GET /api/notice/{id}
     */
    @GetMapping("/{id}")
    public Result<?> getNoticeById(@PathVariable Long id) {
        return noticeService.getNoticeById(id);
    }

    /**
     * 查询最新公告
     * GET /api/notice/latest
     */
    @GetMapping("/latest")
    public Result<?> getLatestNotice() {
        return noticeService.getLatestNotice();
    }
}
