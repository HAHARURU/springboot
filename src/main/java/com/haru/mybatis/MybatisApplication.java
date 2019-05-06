package com.haru.mybatis;

import com.haru.mybatis.listener.InitService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author HARU
 * @since 2018/5/28
 */
@EnableWebMvc
@SpringBootApplication
@EnableCaching
//@EnableScheduling
//@EnableAsync
@MapperScan(basePackages = {"com.haru.mybatis.mapper", "com.haru.mybatis.advice"}) //扫描统一异常包
public class MybatisApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MybatisApplication.class);
        springApplication.addListeners(new InitService());
        springApplication.run(args);
    }
}
