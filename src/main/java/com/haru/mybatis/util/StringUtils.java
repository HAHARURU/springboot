package com.haru.mybatis.util;

import java.util.Random;

/**
 * @author HARU
 * @since 2018/5/28
 */
public class StringUtils {

    private static final String EMPTY_STRING = "";

    /**
     * 格式化字符串
     *
     * @param tplt   模板字符串参数{0},参数2{1}
     * @param params 格式化参数数值
     * @return
     */
    public static String format(String tplt, Object... params) {
        String rslt = tplt;
        for (int i = 0; i < params.length; i++) {
            String strVal = (params[i] == null ? "" : params[i].toString());
            rslt = rslt.replace("{" + i + "}", strVal);
        }
        return rslt;
    }

    /**
     * 生成指定位数的随机正负数字字符串
     *
     * @param bit 数字位数
     * @return 指定位数的随机正负数字字符串
     */
    public static String randomNumberString(int bit) {
        StringBuilder stringBuilder = new StringBuilder(new Random().nextInt(10) >= 5 ? "" : "-");
        for (int i = 0; i < bit; i++) {
            int random = new Random().nextInt(10);
            if (i == 0) {
                random = new Random().nextInt(9);
                if (random == 0) {
                    random++;
                }
            }
            stringBuilder.append(random);
        }
        return stringBuilder.toString();
    }

    /**
     * 判读字符串是否为null或""
     *
     * @param str 需要判断的对象（字符串）
     * @return 判断的结果
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 数据按分隔符拼接成字符串
     *
     * @param array     需要拼接的字符串
     * @param separator 分割线
     * @return 带分隔符的字符串
     */
    public static String join(final Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY_STRING;
        }

        final int noOfItems = array.length;
        if (noOfItems <= 0) {
            return EMPTY_STRING;
        }

        final StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
}
