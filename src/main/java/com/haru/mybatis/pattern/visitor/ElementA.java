package com.haru.mybatis.pattern.visitor;

/**
 * @author HARU
 * @since 2018/10/24
 */
public class ElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
