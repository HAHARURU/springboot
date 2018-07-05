package com.haru.mybatis.enu;

/**
 * @author HARU
 * @since 2018/5/28
 */
public enum ErrorEnum {
    成功(200),
    Timestamp长度不支持Json解析(301),
    TimestampJson解析失败(302);
    int value;

    ErrorEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}