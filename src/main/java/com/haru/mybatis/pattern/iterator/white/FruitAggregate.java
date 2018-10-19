package com.haru.mybatis.pattern.iterator.white;

import java.util.List;

/**
 * @author HARU
 * @since 2018/10/18
 */
public class FruitAggregate implements Aggregate {

    private List<String> fruits;

    public FruitAggregate(List<String> fruits) {
        this.fruits = fruits;
    }

    @Override
    public Iterator getIterator() {
        return new FruitIterator(this);
    }

    // 以下是对外提供的操作聚合元素的方法
    public int getSize() {
        return fruits.size();
    }

    public String getFruit(int index) {
        return fruits.get(index);
    }
}
