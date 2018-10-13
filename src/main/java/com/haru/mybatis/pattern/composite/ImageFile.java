package com.haru.mybatis.pattern.composite;

/**
 * @author HARU
 * @since 2018/10/12
 */
public class ImageFile extends File {
    public ImageFile(String name) {
        super(name);
    }

    @Override
    public void show() {
        System.out.println(name);
    }
}
