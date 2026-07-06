package com.fitness.dto;

import lombok.Data;

/**
 * 注册请求参数
 */
@Data
public class RegisterDTO {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 性别：0-未知 1-男 2-女 */
    private Integer gender;

    /** 手机号 */
    private String phone;
}
