package com.wgy.concurrent2.chapter4observer.multithreading;

/**
 * LifeCycleListener 类是对线程状态进行监视的对象的一种抽象
 *
 * @author Gary
 */
public interface LifeCycleListener {

    /**
     * 这个方法表示当通知行为
     *
     * @param event 事件体
     */
    void onEvent(ObservableRunnable.RunnableEvent event);

}
