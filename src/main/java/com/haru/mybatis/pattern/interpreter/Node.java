package com.haru.mybatis.pattern.interpreter;

/**
 * @author HARU
 * @since 2018/10/26
 */
public interface Node {
    Float interpret(Context context);
}
