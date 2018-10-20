package com.haru.mybatis.pattern.chain;

import org.apache.commons.lang3.StringUtils;

/**
 * @author HARU
 * @since 2018/10/20
 */
public abstract class StringHandler {
    private StringHandler nextHandler;

    public StringHandler setNextHandler(StringHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

    public abstract void handleString(String text);

    void transmit(String text) {
        if (StringUtils.isNotBlank(text) && this.nextHandler != null) {
            this.nextHandler.handleString(text);
        }
    }
}
