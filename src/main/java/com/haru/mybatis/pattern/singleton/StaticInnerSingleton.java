package com.haru.mybatis.pattern.singleton;

/**
 * @author HARU
 * @since 2018/10/4
 */
public class StaticInnerSingleton {
    private StaticInnerSingleton() {
    }

    private static class SingletonHolder {
        private static final StaticInnerSingleton INSTANCE = new StaticInnerSingleton();
    }

    public static StaticInnerSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
