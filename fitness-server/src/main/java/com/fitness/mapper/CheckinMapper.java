package com.fitness.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fitness.dto.StatItemDTO;
import com.fitness.entity.Checkin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 健身打卡 Mapper
 */
@Mapper
public interface CheckinMapper extends BaseMapper<Checkin> {

    /**
     * 查询指定用户今日是否已打卡
     */
    @Select("SELECT COUNT(*) FROM checkin WHERE user_id = #{userId} AND checkin_date = #{today}")
    int countTodayCheckin(@Param("userId") Long userId, @Param("today") LocalDate today);

    /**
     * 查询指定用户本周打卡次数
     */
    @Select("SELECT COUNT(DISTINCT checkin_date) FROM checkin " +
            "WHERE user_id = #{userId} AND checkin_date BETWEEN #{monday} AND #{sunday}")
    int countWeekCheckin(@Param("userId") Long userId,
                         @Param("monday") LocalDate monday,
                         @Param("sunday") LocalDate sunday);

    /**
     * 查询指定用户本月打卡次数
     */
    @Select("SELECT COUNT(DISTINCT checkin_date) FROM checkin " +
            "WHERE user_id = #{userId} " +
            "AND checkin_date >= #{firstDay} AND checkin_date < #{nextMonthFirstDay}")
    int countMonthCheckin(@Param("userId") Long userId,
                          @Param("firstDay") LocalDate firstDay,
                          @Param("nextMonthFirstDay") LocalDate nextMonthFirstDay);

    /**
     * 查询用户连续打卡天数
     */
    @Select("SELECT checkin_date FROM checkin " +
            "WHERE user_id = #{userId} AND checkin_date <= #{today} " +
            "GROUP BY checkin_date ORDER BY checkin_date DESC LIMIT 365")
    List<LocalDate> getCheckinDates(@Param("userId") Long userId,
                                    @Param("today") LocalDate today);

    /**
     * 查询用户每月打卡次数（近6个月）
     */
    @Select("SELECT DATE_FORMAT(checkin_date, '%Y-%m') AS name, " +
            "COUNT(DISTINCT checkin_date) AS value " +
            "FROM checkin " +
            "WHERE user_id = #{userId} " +
            "AND checkin_date >= #{sixMonthsAgo} " +
            "GROUP BY DATE_FORMAT(checkin_date, '%Y-%m') " +
            "ORDER BY name ASC")
    List<StatItemDTO> countMonthlyCheckin(@Param("userId") Long userId,
                                          @Param("sixMonthsAgo") LocalDate sixMonthsAgo);

    /**
     * 查询用户各训练类型占比
     */
    @Select("SELECT sport_type AS name, COUNT(*) AS value " +
            "FROM checkin " +
            "WHERE user_id = #{userId} " +
            "GROUP BY sport_type")
    List<StatItemDTO> countBySportType(@Param("userId") Long userId);

    /**
     * 查询今日打卡人数
     */
    @Select("SELECT COUNT(DISTINCT user_id) FROM checkin WHERE checkin_date = #{today}")
    Long countTodayCheckinUsers(@Param("today") LocalDate today);

    /**
     * 分页查询打卡记录（管理员用，关联用户名和昵称）
     */
    @Select("SELECT c.*, u.username, u.nickname FROM checkin c " +
            "LEFT JOIN user u ON c.user_id = u.id " +
            "WHERE (#{keyword} IS NULL OR #{keyword} = '' " +
            "OR u.username LIKE CONCAT('%', #{keyword}, '%') " +
            "OR u.nickname LIKE CONCAT('%', #{keyword}, '%')) " +
            "ORDER BY c.create_time DESC")
    List<Checkin> selectCheckinPage(@Param("keyword") String keyword);
}
