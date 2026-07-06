package com.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fitness.dto.*;
import com.fitness.entity.Checkin;
import com.fitness.exception.BusinessException;
import com.fitness.mapper.CheckinMapper;
import com.fitness.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 健身打卡服务实现
 */
@Service
public class CheckinServiceImpl extends ServiceImpl<CheckinMapper, Checkin> implements CheckinService {

    @Autowired
    private CheckinMapper checkinMapper;

    /**
     * 新增打卡
     */
    @Override
    public Result<?> addCheckin(Long userId, CheckinDTO dto) {
        // 参数校验
        if (dto.getCheckinDate() == null) {
            return Result.error("打卡日期不能为空");
        }
        if (dto.getCheckinDate().isAfter(LocalDate.now())) {
            return Result.error("打卡日期不能是未来日期");
        }
        if (dto.getSportType() == null || dto.getSportType().trim().isEmpty()) {
            return Result.error("训练类型不能为空");
        }
        if (dto.getDuration() == null || dto.getDuration() < 1 || dto.getDuration() > 600) {
            return Result.error("训练时长应在1-600分钟之间");
        }
        if (dto.getCalorie() == null || dto.getCalorie() < 1 || dto.getCalorie() > 9999) {
            return Result.error("消耗热量应在1-9999千卡之间");
        }

        // 检查同一天同类型是否已打卡
        LambdaQueryWrapper<Checkin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Checkin::getUserId, userId)
                .eq(Checkin::getCheckinDate, dto.getCheckinDate())
                .eq(Checkin::getSportType, dto.getSportType());
        if (this.count(wrapper) > 0) {
            return Result.error("今日该训练类型已打卡，请勿重复打卡");
        }

        // 保存打卡记录
        Checkin checkin = new Checkin();
        checkin.setUserId(userId);
        checkin.setCheckinDate(dto.getCheckinDate());
        checkin.setSportType(dto.getSportType());
        checkin.setDuration(dto.getDuration());
        checkin.setCalorie(dto.getCalorie());
        checkin.setRemark(dto.getRemark());

        this.save(checkin);
        return Result.ok("打卡成功");
    }

    /**
     * 修改打卡
     */
    @Override
    public Result<?> updateCheckin(Long userId, Long checkinId, CheckinDTO dto) {
        Checkin checkin = this.getById(checkinId);
        if (checkin == null) {
            return Result.error("打卡记录不存在");
        }
        if (!checkin.getUserId().equals(userId)) {
            return Result.error("无权修改他人打卡记录");
        }

        // 参数校验
        if (dto.getDuration() != null && (dto.getDuration() < 1 || dto.getDuration() > 600)) {
            return Result.error("训练时长应在1-600分钟之间");
        }
        if (dto.getCalorie() != null && (dto.getCalorie() < 1 || dto.getCalorie() > 9999)) {
            return Result.error("消耗热量应在1-9999千卡之间");
        }

        // 更新字段
        if (dto.getCheckinDate() != null) {
            checkin.setCheckinDate(dto.getCheckinDate());
        }
        if (dto.getSportType() != null) {
            checkin.setSportType(dto.getSportType());
        }
        if (dto.getDuration() != null) {
            checkin.setDuration(dto.getDuration());
        }
        if (dto.getCalorie() != null) {
            checkin.setCalorie(dto.getCalorie());
        }
        if (dto.getRemark() != null) {
            checkin.setRemark(dto.getRemark());
        }

        this.updateById(checkin);
        return Result.ok("修改成功");
    }

    /**
     * 删除打卡
     */
    @Override
    public Result<?> deleteCheckin(Long userId, Long checkinId) {
        Checkin checkin = this.getById(checkinId);
        if (checkin == null) {
            return Result.error("打卡记录不存在");
        }
        if (!checkin.getUserId().equals(userId)) {
            return Result.error("无权删除他人打卡记录");
        }

        this.removeById(checkinId);
        return Result.ok("删除成功");
    }

    /**
     * 分页查询个人打卡记录
     */
    @Override
    public Result<?> pageMyCheckins(Long userId, PageQueryDTO dto) {
        LambdaQueryWrapper<Checkin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Checkin::getUserId, userId)
                .orderByDesc(Checkin::getCheckinDate)
                .orderByDesc(Checkin::getCreateTime);

        Page<Checkin> page = new Page<>(dto.getPage(), dto.getPageSize());
        Page<Checkin> result = this.page(page, wrapper);

        return Result.ok(result);
    }

    /**
     * 查询今日是否打卡
     */
    @Override
    public Result<?> getTodayStatus(Long userId) {
        LocalDate today = LocalDate.now();
        int count = checkinMapper.countTodayCheckin(userId, today);

        Map<String, Object> result = new HashMap<>();
        result.put("checked", count > 0);
        result.put("count", count);
        result.put("date", today);

        return Result.ok(result);
    }

    /**
     * 连续打卡天数（从今天往前计算，直到中断）
     */
    @Override
    public Result<?> getStreak(Long userId) {
        LocalDate today = LocalDate.now();
        List<LocalDate> dates = checkinMapper.getCheckinDates(userId, today);

        // 将打卡日期放入 Set 方便查询
        Set<LocalDate> dateSet = new HashSet<>(dates);

        int streak = 0;
        LocalDate cursor = today;

        // 从今天开始往前数
        while (dateSet.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("streak", streak);

        return Result.ok(result);
    }

    /**
     * 本周打卡次数
     */
    @Override
    public Result<?> getWeekCount(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        int count = checkinMapper.countWeekCheckin(userId, monday, sunday);

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("monday", monday);
        result.put("sunday", sunday);

        return Result.ok(result);
    }

    /**
     * 本月打卡次数
     */
    @Override
    public Result<?> getMonthCount(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate nextMonthFirstDay = firstDay.plusMonths(1);

        int count = checkinMapper.countMonthCheckin(userId, firstDay, nextMonthFirstDay);

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("month", today.getMonthValue());

        return Result.ok(result);
    }

    /**
     * 每月打卡趋势（近6个月）
     */
    @Override
    public Result<?> getMonthlyStats(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate sixMonthsAgo = today.minusMonths(5).withDayOfMonth(1);

        List<StatItemDTO> stats = checkinMapper.countMonthlyCheckin(userId, sixMonthsAgo);
        return Result.ok(stats);
    }

    /**
     * 训练类型占比
     */
    @Override
    public Result<?> getTypeStats(Long userId) {
        List<StatItemDTO> stats = checkinMapper.countBySportType(userId);
        return Result.ok(stats);
    }

    // ========== 管理员接口 ==========

    /**
     * 分页查询所有打卡记录（管理员）
     */
    @Override
    public Result<?> pageAllCheckins(PageQueryDTO dto) {
        Page<Checkin> page = new Page<>(dto.getPage(), dto.getPageSize());

        // 使用自定义 SQL 联表查询
        List<Checkin> records = checkinMapper.selectCheckinPage(dto.getKeyword());

        // 手动分页
        int total = records.size();
        int fromIndex = (int) ((dto.getPage() - 1) * dto.getPageSize());
        int toIndex = Math.min(fromIndex + dto.getPageSize(), total);

        List<Checkin> pageRecords;
        if (fromIndex >= total) {
            pageRecords = Collections.emptyList();
        } else {
            pageRecords = records.subList(fromIndex, toIndex);
        }

        page.setRecords(pageRecords);
        page.setTotal(total);

        return Result.ok(page);
    }

    /**
     * 管理员删除打卡
     */
    @Override
    public Result<?> adminDeleteCheckin(Long checkinId) {
        Checkin checkin = this.getById(checkinId);
        if (checkin == null) {
            return Result.error("打卡记录不存在");
        }
        this.removeById(checkinId);
        return Result.ok("删除成功");
    }
}
