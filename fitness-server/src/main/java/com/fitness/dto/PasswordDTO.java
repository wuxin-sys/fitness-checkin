package com.fitness.dto;

import lombok.Data;

/**
 * 修改密码请求参数
 */
@Data
public class PasswordDTO {

    /** 旧密码 */
    private String oldPassword;

    /** 新密码 */
    private String newPassword;
}
