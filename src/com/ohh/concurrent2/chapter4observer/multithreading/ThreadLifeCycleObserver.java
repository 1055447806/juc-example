package com.ohh.concurrent2.chapter4observer.multithreading;

/**
 * 线程执行状态监听类
 *
 * @author Gary
 */
public class ThreadLifeCycleObserver implements LifeCycleListener {

    /**
     * 用于统计通知总数的变量
     */
    private int count;

    /**
     * 通知方法, 在控制台打印查询的进度和结果
     *
     * @param event 查询结果的实例
     */
    @Override
    public synchronized void onEvent(ObservableRunnable.RunnableEvent event) {
        String msg = "Event[%d]\t-> Thread[%s] state: %s" +
                (event.getState() == ObservableRunnable.RunnableState.ERROR ? " cause: %s" : "");
        System.out.println(String.format(msg,
                ++this.count, event.getThread().getName(), event.getState(), event.getCause()));
    }
}
