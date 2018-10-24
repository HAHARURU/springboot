package com.haru.mybatis.pattern.visitor;

/**
 * @author HARU
 * @since 2018/10/24
 */
public class VisitorA extends Visitor {
    @Override
    public void visit(ElementA elementA) {
        System.out.println(this + "关于" + elementA + "的相关操作");
    }

    @Override
    public void visit(ElementB elementB) {
        System.out.println(this + "关于" + elementB + "的相关操作");
    }
}
