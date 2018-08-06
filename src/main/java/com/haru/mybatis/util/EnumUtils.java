package com.haru.mybatis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author HARU
 * @since 2018/5/24
 */
public class EnumUtils {
    static Logger logger = LoggerFactory.getLogger(EnumUtils.class);

    /**
     * 通过枚举文本字段，获得枚举类型的常量
     *
     * @param enumCls 枚举类型
     * @param text    枚举文本
     * @return
     * @throws Exception 转换错误
     */
    public static Enum getEnum(Class enumCls, String text) {
        try {
            for (Object o : enumCls.getEnumConstants()) {
                if (o.toString().equals(text)) {
                    return (Enum) o;
                }
            }
        } catch (Exception e) {
            logger.error("枚举转换失败", e);
        }

        return null;
    }

    /**
     * 通过value字段值，获得枚举类型的常量
     *
     * @param enumCls
     * @param value
     * @return
     * @throws Exception 转换错误
     */
    public static Enum getEnum(Class enumCls, int value) {
        try {
            Field valueField = enumCls.getDeclaredField("value");
            ReflectionUtils.makeAccessible(valueField);
            for (Object o : enumCls.getEnumConstants()) {
                int fVal = (Integer) ReflectionUtils.getField(valueField, o);
                if (value == fVal) {
                    return (Enum) o;
                }
            }

        } catch (Exception e) {
            logger.error("枚举转换失败", e);
        }

        return null;
    }
}
