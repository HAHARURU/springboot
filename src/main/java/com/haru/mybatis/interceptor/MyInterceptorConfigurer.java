package com.haru.mybatis.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author HARU
 * @since 2018/7/30
 */
@Configuration
public class MyInterceptorConfigurer extends WebMvcConfigurerAdapter {
   
    @Bean
    public MyInterceptor  getMyInterceptor (){
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new getMyInterceptor()).addPathPatterns("/**").excludePathPatterns("/country/**");
        super.addInterceptors(registry);
    }
}
