package com.haru.mybatis.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author HARU
 * @since 2018/10/9
 */
public class DynamicProxyHandler implements InvocationHandler {
    private Object object;

    public DynamicProxyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start");
        Object invoke = method.invoke(object, args);
        System.out.println("end");
        return invoke;
    }
}
