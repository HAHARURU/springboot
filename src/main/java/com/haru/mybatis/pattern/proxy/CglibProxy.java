package com.haru.mybatis.pattern.proxy;

import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author HARU
 * @since 2018/10/9
 */
public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer;

    public CglibProxy() {
        this.enhancer = new Enhancer();
    }

    public Object getInstance(Class cls) {
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("start");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("end");
        return result;
    }
}
