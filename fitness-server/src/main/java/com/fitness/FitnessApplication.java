package com.fitness;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 健身打卡系统 - Spring Boot 启动类
 */
@SpringBootApplication
@MapperScan("com.fitness.mapper")
public class FitnessApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessApplication.class, args);
        System.out.println("========================================");
        System.out.println("  健身打卡系统后端服务启动成功！");
        System.out.println("  访问地址: http://localhost:8080");
        System.out.println("========================================");
    }
}
