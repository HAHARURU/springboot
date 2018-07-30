package com.haru.mybatis.enumPackage;

/**
 * @author HARU
 * @since 2018/5/28
 */
public enum ErrorEnum {
    成功(200),
    Timestamp长度不支持Json解析(301),
    TimestampJson解析失败(302),
    保存失败(303),
    不存在无法保存(304),
    更新失败(305),
    签名错误(306);
    int value;

    ErrorEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
