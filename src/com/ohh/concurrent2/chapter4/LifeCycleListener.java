package com.ohh.concurrent2.chapter4;

/**
 * 观察者接口
 */
public interface LifeCycleListener {

    /**
     * 通知方法, 报告逻辑代码的执行进度和结果
     *
     * @param event 结果实例
     */
    void onEvent(ObservableRunnable.RunnableEvent event);

}
