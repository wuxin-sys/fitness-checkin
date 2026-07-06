package com.fitness.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员实体类
 */
@Data
@TableName("admin")
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 管理员用户名 */
    private String username;

    /** 密码（MD5加盐加密） */
    private String password;

    /** 创建时间 */
    private LocalDateTime createTime;
}
