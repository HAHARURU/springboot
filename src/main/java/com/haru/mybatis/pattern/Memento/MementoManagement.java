package com.haru.mybatis.pattern.Memento;

/**
 * @author HARU
 * @since 2018/10/22
 */
public class MementoManagement {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
