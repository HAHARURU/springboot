package com.haru.mybatis.util;

import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合操作工具类
 *
 * @author HARU
 * @date 2019/5/4
 **/
@Log4j
public class CollectionUtils {

    /**
     * 通过下标从list中获取新的list，若indexes长度为0则返回原来list，index需从1开始，因为当index为0时，得到的字段是
     * serialVersionUID不需要进行处理，最多为list的长度减1；出错情况下返回一个空list。
     *
     * @param originList 源list集合
     * @param indexes    list下标
     * @param <T>        list集合类型
     * @return 新的list集合
     */
    public static <T> List<T> getListByIndex(List<T> originList, int... indexes) {
        if (indexes.length == 0) {
            return originList;
        }
        List<T> listByIndexes = new ArrayList<>();
        for (int index : indexes) {
            if (index == 0) {
                log.error("下标应从1开始");
                return new ArrayList<>();
            }
            if (index >= originList.size()) {
                log.error("list最大长度为" + originList.size());
                return new ArrayList<>();
            }
            listByIndexes.add(originList.get(index));
        }
        return listByIndexes;
    }

    /**
     * 创建list并添加元素，elements长度为0时返回null
     *
     * @param listType 想要的list类型
     * @param elements  添加的元素
     * @return list集合
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> List<T> createList(Class<? extends List> listType, T... elements) {
        List<T> listWithType = null;
        try {
            listWithType = listType.newInstance();
            listWithType.addAll(Arrays.asList(elements));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return listWithType;
    }
}
