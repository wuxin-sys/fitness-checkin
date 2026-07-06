package com.fitness.interceptor;

import com.fitness.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户端 JWT 拦截器
 * 校验请求头中的 Token，解析用户信息并存入 request 属性
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 从请求头获取 Token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new com.fitness.exception.BusinessException(401, "未登录，请先登录");
        }

        // 去掉 "Bearer " 前缀（如果有的话）
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 校验 Token
        if (!jwtUtil.validateUserToken(token)) {
            throw new com.fitness.exception.BusinessException(401, "登录已过期，请重新登录");
        }

        // 解析用户信息，存入 request 属性，方便 Controller 获取
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);

        return true;
    }
}
