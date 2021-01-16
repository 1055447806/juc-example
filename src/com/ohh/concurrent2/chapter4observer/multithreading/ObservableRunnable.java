package com.ohh.concurrent2.chapter4observer.multithreading;

/**
 * 实现了 Runnable 接口的主题
 * <p>
 * 该主题本质是一个 Runnable 接口的实现类，
 * 其中包含了一个 listener 表示对该 Runnable 进行监听的对象，
 *
 * @author Gary
 */
public abstract class ObservableRunnable implements Runnable {

    /**
     * 用来监控该主题的观察者实例
     */
    protected LifeCycleListener listener;

    /**
     * 构造函数，通过构造参数为其指定一个监听对象
     *
     * @param listener 对该线程进行监听的对象
     */
    public ObservableRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    /**
     * 子类应该通过重写这个类，来实现实际执行的代码逻辑
     */
    protected abstract void execute() throws Throwable;

    /**
     * 通过重写 Runnable 接口的 run 方法，实现了对监听者对象的主动通知，
     * 分别在方法执行 “开始”，“结束”，“异常” 三个位置进行了通知。
     */
    @Override
    public void run() {
        try {
            notifyChange(new RunnableEvent(RunnableState.START, Thread.currentThread(), null));
            execute();
            notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
        } catch (Throwable e) {
            notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
        }
    }

    /**
     * 通知方法，用于向监听对象发送状态变更通知
     *
     * @param event 相应的事件或结果
     */
    protected void notifyChange(final RunnableEvent event) {
        listener.onEvent(event);
    }

    /**
     * Runnable 的执行状态
     */
    public enum RunnableState {
        START, ERROR, DONE;
    }

    /**
     * 该类是对事件的封装，
     * 其中包含了：
     * Runnable 的执行状态 state，
     * Runnable 的执行线程 thread，
     * Runnable 执行时出现的异常 cause，
     */
    public static class RunnableEvent {

        private final RunnableState state;
        private final Thread thread;
        private final Throwable cause;

        public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
            this.state = state;
            this.thread = thread;
            this.cause = cause;
        }

        public RunnableState getState() {
            return state;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getCause() {
            return cause;
        }
    }
}
