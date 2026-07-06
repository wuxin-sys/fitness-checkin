package com.fitness.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 负责 Token 的生成与校验
 */
@Component
public class JwtUtil {

    /** 用户端密钥 */
    @Value("${jwt.secret}")
    private String secret;

    /** 管理员密钥 */
    @Value("${jwt.admin-secret}")
    private String adminSecret;

    /** Token 过期时间（毫秒） */
    @Value("${jwt.expiration}")
    private Long expiration;

    /** 用户ID在 claims 中的 key */
    private static final String CLAIM_USER_ID = "userId";

    /** 用户名在 claims 中的 key */
    private static final String CLAIM_USERNAME = "username";

    // ========== 用户端 Token ==========

    /**
     * 生成用户 Token
     */
    public String generateUserToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USER_ID, userId);
        claims.put(CLAIM_USERNAME, username);
        return createToken(claims, secret);
    }

    /**
     * 从用户 Token 中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token, secret);
        return claims.get(CLAIM_USER_ID, Long.class);
    }

    /**
     * 从用户 Token 中获取用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token, secret);
        return claims.get(CLAIM_USERNAME, String.class);
    }

    /**
     * 校验用户 Token 是否有效
     */
    public boolean validateUserToken(String token) {
        try {
            parseToken(token, secret);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ========== 管理员 Token ==========

    /**
     * 生成管理员 Token
     */
    public String generateAdminToken(Long adminId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USER_ID, adminId);
        claims.put(CLAIM_USERNAME, username);
        return createToken(claims, adminSecret);
    }

    /**
     * 从管理员 Token 中获取管理员ID
     */
    public Long getAdminIdFromToken(String token) {
        Claims claims = parseToken(token, adminSecret);
        return claims.get(CLAIM_USER_ID, Long.class);
    }

    /**
     * 校验管理员 Token 是否有效
     */
    public boolean validateAdminToken(String token) {
        try {
            parseToken(token, adminSecret);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ========== 内部方法 ==========

    /**
     * 创建 Token
     */
    private String createToken(Map<String, Object> claims, String signingKey) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    /**
     * 解析 Token
     */
    private Claims parseToken(String token, String signingKey) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
