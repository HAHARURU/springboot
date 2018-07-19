package com.haru.mybatis.util.http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author HARU
 * @since 2018/5/28
 */
public class HashUtils {
    /**
     * 通过数组快速创建参数Map (有序的LinkedHashMap)
     *
     * @param params key1,value1,key2,value2,key3,value3 ...
     * @return map
     */
    public static Map getMap(Object... params) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        if (params.length % 2 != 0) {
            throw new RuntimeException("键值对必须为偶数个");
        }

        for (int i = 0; i < params.length; ) {
            map.put(params[i].toString(), params[i + 1]);
            i += 2;
        }
        return map;
    }

    /**
     * 获取String值
     *
     * @param rec
     * @param key
     */
    public static String getStringValue(Map rec, String key) {
        return rec == null || rec.get(key) == null ? null : String.valueOf(rec.get(key));
    }
}
