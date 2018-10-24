package com.haru.mybatis.pattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HARU
 * @since 2018/10/24
 *
 * 对象结构，一个元素的集合，它用于存放元素对象，并且提供了遍历其内部元素的方法
 */
public class ObjectStructure {
    private List<Element> elementList;

    public ObjectStructure() {
        this.elementList = new ArrayList<>();
    }

    public void addElement(Element element) {
        elementList.add(element);
    }

    public void removeElement(Element element) {
        elementList.remove(element);
    }

    public void accept(Visitor visitor) {
        elementList.forEach(element -> element.accept(visitor));
    }
}
