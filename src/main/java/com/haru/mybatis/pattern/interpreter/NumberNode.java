package com.haru.mybatis.pattern.interpreter;

/**
 * @author HARU
 * @since 2018/10/26
 */
public class NumberNode implements Node {
    private String key;

    public NumberNode(String key) {
        this.key = key;
    }

    @Override
    public Float interpret(Context context) {
        return context.getNode().get(key);
    }
}
