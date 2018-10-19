package com.haru.mybatis.pattern.iterator.white;

/**
 * @author HARU
 * @since 2018/10/18
 *
 *  * 外部迭代接口
 */
public interface Iterator {
    boolean hasNext();

    String next();
}
