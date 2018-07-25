package com.haru.mybatis.enumPackage;

/**
 * @author HARU
 * @since 2018/5/28
 */
public enum StateEnum {
    启用(1), 暂停(2), 禁止(0);

    int value;

    StateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
