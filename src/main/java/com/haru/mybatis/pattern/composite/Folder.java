package com.haru.mybatis.pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HARU
 * @since 2018/10/12
 */
public class Folder extends File {
    private List<File> files;

    public Folder(String name) {
        super(name);
        this.files = new ArrayList<>();
    }

    @Override
    public void show() {
        System.out.println(name);
        files.forEach(File::show);
    }

    public void add(File file) {
        files.add(file);
    }
}
