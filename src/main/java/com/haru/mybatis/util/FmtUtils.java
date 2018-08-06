package com.haru.mybatis.util;

import java.text.SimpleDateFormat;

/**
 * @author HARU
 * @since 2018/8/2
 */
public class FmtUtils {
    public static SimpleDateFormat getDateFmt(int len) {
        SimpleDateFormat dateFmt = null;
        if (len == 19)
            dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else if (len == 16)
            dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        else if (len == 10)
            dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        return dateFmt;
    }
}
