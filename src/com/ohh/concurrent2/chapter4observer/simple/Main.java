package com.ohh.concurrent2.chapter4observer.simple;

/**
 * Observer 模式
 * 实现了当对象状态发生变更时，对所有依赖于它的对象进行主动通知的意图。
 * <pre>
 *     +-----------+   If state is changed   +----------+
 *     |  Subject  | +---------------------> | Observer |
 *     +-----------+         notify()        +----------+
 * </pre>
 * 定义一个主题事件，并对其添加两个监听者，BinaryObserver 和 OctalObserver
 *
 * @author Gary
 */
public class Main {
    public static void main(String[] args) {
        // 定义一个主题
        final Subject subject = new Subject();
        // 为主题添加一个监听者 BinaryObserver
        new BinaryObserver(subject);
        // 为主题添加一个监听者 OctalObserver
        new OctalObserver(subject);

        // 使主题的状态发生变更，并使其主动对其监听者进行通知
        subject.setState(10);
        subject.setState(10);
        subject.setState(15);
    }
}
