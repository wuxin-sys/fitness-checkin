package com.fitness.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具类
 * 使用双层 MD5 + 固定盐值
 */
public class PasswordUtil {

    /** 固定盐值 */
    private static final String SALT = "fitness_checkin_salt";

    /**
     * 加密密码
     * 算法：MD5(MD5(原始密码) + salt)
     *
     * @param rawPassword 原始密码
     * @return 加密后的16进制字符串
     */
    public static String encrypt(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            return "";
        }
        String firstMd5 = md5(rawPassword);
        return md5(firstMd5 + SALT);
    }

    /**
     * 校验密码
     *
     * @param rawPassword     原始密码
     * @param encryptedPassword 数据库中存储的加密密码
     * @return 是否匹配
     */
    public static boolean match(String rawPassword, String encryptedPassword) {
        if (rawPassword == null || encryptedPassword == null) {
            return false;
        }
        return encrypt(rawPassword).equals(encryptedPassword);
    }

    /**
     * MD5 哈希
     */
    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }

    // 测试用：生成加密密码
    public static void main(String[] args) {
        System.out.println("admin123 加密后: " + encrypt("admin123"));
        System.out.println("123456 加密后:  " + encrypt("123456"));
    }
}
