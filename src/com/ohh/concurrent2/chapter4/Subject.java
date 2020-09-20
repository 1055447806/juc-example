package com.ohh.concurrent2.chapter4;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        System.out.println("\033[1;46;32m" + "===== set state: " + state + " =====" + "\033[0m");
        if (this.state == state) {
            return;
        }
        this.state = state;
        notifyAllObserver();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObserver() {
        observers.stream().forEach(Observer::update);
    }
}
