package com.haru.mybatis.listener;

import com.haru.mybatis.generation.component.GenerateBeanComponent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 上下文已经准备完毕的时候触发的事件
 *
 * @author HARU
 * @date 2019/5/6
 **/
public class InitService implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
        GenerateBeanComponent generateBeanComponent = applicationContext.getBean(GenerateBeanComponent.class);
        System.out.println(generateBeanComponent.getClass().getName());
    }
}
