package com.haru.mybatis.pattern.proxy;

/**
 * @author HARU
 * @since 2018/10/9
 */
public class HouseImpl implements House {
    @Override
    public void buy() {
        System.out.println("buy house");
    }
}
