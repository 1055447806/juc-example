package com.ohh.concurrent2.chapter4;

import java.util.List;

/**
 * 观察工作线程的观察者类
 */
public class ThreadLifeCycleObserver implements LifeCycleListener {

    private final Object LOCK = new Object();

    /**
     * 用于统计通知总数的变量
     */
    private int i;

    /**
     * 模拟查询方法. 对于每一个id, 启动一个工作线程对其进行查询, 并在查询前后以及失败时进行通知
     *
     * @param ids 需要进行查询的id集合
     */
    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.forEach(id ->
                new Thread(new ObservableRunnable(this) {
                    @Override
                    public void run() {
                        try {
                            notifyChange(new RunnableEvent(RunnableState.RUNNABLE, Thread.currentThread(), null));
                            System.out.println("query for the id " + id);
                            Thread.sleep(1000);
                            // int i = 1 / 0;
                            notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                        } catch (Exception e) {
                            notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                        }
                    }
                }, id).start()
        );
    }

    /**
     * 通知方法, 在控制台打印查询的进度和结果
     *
     * @param event 查询结果的实例
     */
    @Override
    public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println(++i + ": The runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getState() + "]");
            if (event.getCause() != null) {
                System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}
