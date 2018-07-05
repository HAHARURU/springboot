package com.haru.mybatis.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author HARU
 * @since 2018/7/5
 */
public class SortCollection {
    /**
     * api接口排序
     *
     * @param appsecret appsecret
     * @param map       map
     * @return 排序结果
     */
    public static String sort(String appsecret, ConcurrentSkipListMap<String, ?> map) {
        StringBuilder s = new StringBuilder();
        // method数组值处理
        Object method = map.get("httpMethod");
        if (method != null) {
            if (method instanceof List) {
                s.append(((List)method).get(0));
            } else if (method instanceof Object[]) {
                s.append(((String[])method)[0]);
            } else {
                s.append(method);
            }
        }
        // url数组值处理
        Object url = map.get("requestURI");
        if (url != null) {
            if (url instanceof List) {
                s.append(((List)url).get(0));
            } else if (url instanceof Object[]) {
                s.append(((String[])url)[0]);
            } else {
                s.append(url);
            }
        }
        // 删除url和method参数
        map.remove("httpMethod");
        map.remove("requestURI");
        Map<String, Object> sortMap = SortCollection.sortMapByKey(map);
        s.append(appendMap(sortMap));
        s.append(appsecret);
        String signStr = null;
        try {
            signStr = URLEncoder.encode(s.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return signStr;
    }

    /**
     * 对Map按key进行排序，支持map和list混合嵌套
     *
     * @param map 参数map
     * @return 排序结果
     */
    public static Map<String, Object> sortMapByKey(ConcurrentSkipListMap<String, ?> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new ConcurrentSkipListMap<String, Object>(new MapComparator());
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                sortMap.put(entry.getKey(), sortMapByKey((ConcurrentSkipListMap) entry.getValue()));
                continue;
            }
            if (entry.getValue() instanceof List) {
                sortMap.put(entry.getKey(), sortListByValue((List) entry.getValue()));
                continue;
            }
            if (entry.getValue() instanceof String[]) {
                String[] temp = (String[]) entry.getValue();
                sortMap.put(entry.getKey(), sortListByValue(Arrays.asList(temp)));
                continue;
            }
            if (entry.getValue() instanceof String) {
                sortMap.put(entry.getKey(), entry.getValue());
                continue;
            }
            if (entry.getValue() instanceof Boolean) {
                sortMap.put(entry.getKey(), entry.getValue().toString());
                continue;
            }
            if (entry.getValue() instanceof Number) {
                sortMap.put(entry.getKey(), entry.getValue());
                continue;
            }
        }
        return sortMap;
    }

    /**
     * 对list进行排序，支持map和list混合嵌套
     *
     * @param list 排序的list
     * @return 排序结果
     */
    public static List<Object> sortListByValue(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<Object> sortList = new ArrayList<Object>();
        for (Object item : list) {
            if (item instanceof ConcurrentSkipListMap) {
                sortList.add(sortMapByKey((ConcurrentSkipListMap) item));
                continue;
            }
            if (item instanceof List) {
                sortList.add(sortListByValue((List) item));
                continue;
            }
            if (item instanceof String[]) {
                sortList.add(sortListByValue(Arrays.asList((String[]) item)));
                continue;
            }
            if (item instanceof String) {
                sortList.add(item);
                continue;
            }
            if (item instanceof Boolean) {
                sortList.add(item);
                continue;
            }
            if (item instanceof Number) {
                sortList.add(item);
                continue;
            }

        }
        Collections.sort(sortList, new MapComparator());
        return sortList;
    }

    /**
     * 获取map的key和value链接字符串，支持map和list混合嵌套
     *
     * @param map 排序map
     * @return 排序结果
     */
    public static String appendMap(Map<String, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                sb.append(entry.getKey());
                sb.append(appendMap((Map) entry.getValue()));
                continue;
            }
            if (entry.getValue() instanceof List) {
                sb.append(entry.getKey());
                sb.append(appendList((List) entry.getValue()));
                continue;
            }
            if (entry.getValue() instanceof String[]) {
                sb.append(entry.getKey());
                String[] temp = (String[]) entry.getValue();
                sb.append(appendList(Arrays.asList(temp)));
                continue;
            }
            if (entry.getValue() instanceof String) {
                sb.append(entry.getKey());
                sb.append(entry.getValue());
                continue;
            }
            if (entry.getValue() instanceof Boolean) {
                sb.append(entry.getKey());
                sb.append(entry.getValue());
                continue;
            }
            if (entry.getValue() instanceof Number) {
                sb.append(entry.getKey());
                sb.append(entry.getValue());
                continue;
            }
        }
        return sb.toString();
    }

    /**
     * 获取list链接字符串，支持map和list混合嵌套
     *
     * @param list 排序的list
     * @return 排序结果
     */
    public static String appendList(List list) {
        StringBuilder sb = new StringBuilder();
        for (Object item : list) {
            if (item instanceof Map) {
                sb.append(appendMap((Map) item));
                continue;
            }
            if (item instanceof List) {
                sb.append(appendList((List) item));
                continue;
            }
            if (item instanceof String[]) {
                String[] temp = (String[]) item;
                sb.append(appendList(Arrays.asList(temp)));
                continue;
            }
            if (item instanceof String) {
                sb.append(item);
                continue;
            }
            if (item instanceof Boolean) {
                sb.append(item);
                continue;
            }
            if (item instanceof Number) {
                sb.append(item);
                continue;
            }
        }
        return sb.toString();
    }
}

/**
 * 比较器类
 */
class MapComparator implements Comparator<Object> {
    /**
     * 比较器
     *
     * @param o1 参数
     * @param o2 参数
     * @return 结果
     */
    @Override
    public int compare(Object o1, Object o2) {
        return o1.toString().compareTo(o2.toString());
    }
}


