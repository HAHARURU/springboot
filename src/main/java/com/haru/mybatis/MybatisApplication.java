package com.haru.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author HARU
 * @since 2018/5/28
 */
@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = {"com.haru.mybatis.mapper", "com.haru.mybatis.advice"}) //扫描面统一异常包
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}
