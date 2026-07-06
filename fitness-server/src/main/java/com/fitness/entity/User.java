package com.fitness.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码（MD5加盐加密） */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 性别：0-未知 1-男 2-女 */
    private Integer gender;

    /** 手机号 */
    private String phone;

    /** 角色：0-普通用户 1-管理员 */
    private Integer role;

    /** 头像地址 */
    private String avatar;

    /** 创建时间 */
    private LocalDateTime createTime;
}
