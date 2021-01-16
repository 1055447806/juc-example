package com.ohh.concurrent2.chapter4observer.simple;

/**
 * OctalObserver 监听者实现类
 * <p>
 * 该监听者订阅主题事件，并在主题的状态发生变更后，
 * 以八进制的形式对其状态进行打印输出。
 *
 * @author Gary
 */
public class OctalObserver extends Observer {

    /**
     * 构造函数
     *
     * @param subject 主题事件
     */
    public OctalObserver(Subject subject) {
        super(subject);
    }

    /**
     * 该方法定义了通知的具体的实现形式，在这里，
     * 通知会以八进制的形式对主题的状态进行打印输出。
     */
    @Override
    public void update() {
        System.out.println("Octal String: " + Integer.toOctalString(subject.getState()));
    }
}
