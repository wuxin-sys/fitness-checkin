package com.fitness.interceptor;

import com.fitness.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员拦截器
 * 校验管理员 Token，解析管理员信息
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new com.fitness.exception.BusinessException(401, "未登录，请先登录");
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.validateAdminToken(token)) {
            throw new com.fitness.exception.BusinessException(403, "无管理员权限");
        }

        Long adminId = jwtUtil.getAdminIdFromToken(token);
        request.setAttribute("adminId", adminId);

        return true;
    }
}
