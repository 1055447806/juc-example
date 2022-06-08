package com.wgy.concurrent2.chapter4observer.simple;


/**
 * BinaryObserver 监听者实现类
 * <p>
 * 该监听者订阅主题事件，并在主题的状态发生变更后，
 * 以二进制的形式对其状态进行打印输出。
 *
 * @author Gary
 */
public class BinaryObserver extends Observer {

    /**
     * 构造函数
     *
     * @param subject 主题事件
     */
    public BinaryObserver(Subject subject) {
        super(subject);
    }

    /**
     * 该方法定义了通知的具体的实现形式，在这里，
     * 通知会以二进制的形式对主题的状态进行打印输出。
     */
    @Override
    public void update() {
        System.out.println("Binary String: " + Integer.toBinaryString(subject.getState()));
    }
}
