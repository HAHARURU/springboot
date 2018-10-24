package com.haru.mybatis.pattern.visitor;

/**
 * @author HARU
 * @since 2018/10/24
 */
public abstract class Visitor {
    public abstract void visit(ElementA elementA);
    public abstract void visit(ElementB elementB);
}
