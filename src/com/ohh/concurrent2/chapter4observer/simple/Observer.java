package com.ohh.concurrent2.chapter4observer.simple;

/**
 * Observer 是监听者类的抽象，监听者类可以订阅某个主题，
 * 并接收来自这个主题发送的消息。
 *
 * @author Gary
 */
public abstract class Observer {


    /**
     * 订阅的主题事件
     */
    protected Subject subject;


    /**
     * 通过给构造函数传递一个主题事件，将监听者和主题事件进行绑定
     *
     * @param subject 订阅的主题事件
     */
    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    /**
     * 当事件被触发时，被执行的函数
     */
    public abstract void update();
}
