package com.haru.mybatis.pattern.memento;

/**
 * @author HARU
 * @since 2018/10/22
 */
public class Origin {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Memento createMemento() {
        return new Memento(value);
    }

    public void getMementoState(Memento memento) {
        this.value = memento.getValue();
    }
}
