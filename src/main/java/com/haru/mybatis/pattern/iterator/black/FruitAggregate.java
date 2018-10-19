package com.haru.mybatis.pattern.iterator.black;

import com.haru.mybatis.pattern.iterator.white.Aggregate;
import com.haru.mybatis.pattern.iterator.white.Iterator;

import java.util.List;

/**
 * @author HARU
 * @since 2018/10/19
 */
public class FruitAggregate implements Aggregate {

    private List<String> fruits;

    public FruitAggregate(List<String> fruits) {
        this.fruits = fruits;
    }

    @Override
    public Iterator getIterator() {
        return new FruitIterator();
    }

    // 内部类
    private class FruitIterator implements Iterator {

        private int size;
        private int index;

        public FruitIterator() {
            this.size = fruits.size();
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public String next() {
            return fruits.get(index++);
        }
    }
}
