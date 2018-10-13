package com.haru.mybatis.pattern.composite;

/**
 * @author HARU
 * @since 2018/10/12
 */
public class VideoFile extends File {
    public VideoFile(String name) {
        super(name);
    }

    @Override
    public void show() {
        System.out.println(name);
    }
}
