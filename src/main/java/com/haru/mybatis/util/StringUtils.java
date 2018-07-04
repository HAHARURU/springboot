package com.haru.mybatis.util;

/**
 * @author HARU
 * @since 2018/5/28
 */
public class StringUtils {
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
}
