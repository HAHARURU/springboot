package com.haru.mybatis.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HARU
 * @since 2018/10/16
 */
public class Weather {
    private List<User> observers;
    private String message;

    public Weather() {
        this.observers = new ArrayList<>();
    }

    public void registerObserver(User user) {
        observers.add(user);
    }

    public void removeObserver(User user) {
        observers.remove(user);
    }

    public void notifyObserver() {
        observers.forEach(observer -> observer.update(message));
    }

    public void setMessage(String message) {
        this.message = message;
        notifyObserver();
    }
}
