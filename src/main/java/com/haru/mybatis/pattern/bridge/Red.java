package com.haru.mybatis.pattern.bridge;

/**
 * @author HARU
 * @since 2018/10/10
 */
public class Red implements Color {
    @Override
    public void paint() {
        System.out.println("paint red");
    }
}
