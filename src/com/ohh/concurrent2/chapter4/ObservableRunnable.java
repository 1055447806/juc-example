package com.ohh.concurrent2.chapter4;

/**
 * 主题类
 */
public abstract class ObservableRunnable implements Runnable {

    /**
     * 用来监控该主题的观察者实例
     */
    protected LifeCycleListener listener;

    public ObservableRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    /**
     * 提交相应的事件或结果, 并调用观察者实例的onEvent方法对结果进行打印
     *
     * @param event 相应的事件或结果
     */
    protected void notifyChange(final RunnableEvent event) {
        listener.onEvent(event);
    }

    /**
     * 工作线程的状态
     */
    public enum RunnableState {
        RUNNABLE, ERROR, DONE;
    }

    /**
     * 用来封装事件或结果信息的结果类
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
