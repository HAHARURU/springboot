package com.haru.mybatis.pattern.iterator.white;

/**
 * @author HARU
 * @since 2018/10/18
 */
public class FruitIterator implements Iterator {

    private int size;
    private int index;
    private FruitAggregate fruitAggregate;

    public FruitIterator(FruitAggregate fruitAggregate) {
        this.size = fruitAggregate.getSize();
        this.index = 0;
        this.fruitAggregate = fruitAggregate;
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public String next() {
        return fruitAggregate.getFruit(index++);
    }
}
