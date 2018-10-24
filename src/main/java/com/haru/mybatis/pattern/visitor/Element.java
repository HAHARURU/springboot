package com.haru.mybatis.pattern.visitor;

/**
 * @author HARU
 * @since 2018/10/24
 *
 * 元素类，在其中一般都定义了一个accept()方法，用于接受访问者的访问
 */
public interface Element {
    void accept(Visitor visitor);
}
