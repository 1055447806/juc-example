package com.wgy.concurrent2.chapter4observer.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject 类表示某个 “主题事件”，这个事件会拥有多个监听者，
 * 监听者可以订阅这些事件，当事件发生时，则将消息发送给监听者，
 *
 * @author Gary
 */
public class Subject {

    /**
     * 表示监听者集合的列表
     */
    private List<Observer> observers = new ArrayList<>();

    /**
     * 主题的状态，当状态被改变时，会执行监听者的 update 方法进行通知
     */
    private int state;

    /**
     * 返回主题的状态
     *
     * @return 主题的状态
     */
    public int getState() {
        return this.state;
    }

    /**
     * 设置主题的状态，并对其进行判断，如果状态发生了改变，
     * 则执行 notifyAllObserver 方法向监听者发送通知。
     *
     * @param state 主题的状态
     */
    public void setState(int state) {
        if (this.state != state) {
            this.state = state;
            notifyAllObserver();
        }

    }

    /**
     * 为当前的主题事件添加一个监听者
     *
     * @param observer 监听者
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 向所有监听者发送通知
     */
    public void notifyAllObserver() {
        observers.forEach(Observer::update);
    }
}
