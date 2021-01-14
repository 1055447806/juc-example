package com.ohh.concurrent2.chapter8future;

/**
 * AsyncFutureTask 是一个容器，这个容器包含了
 * 一个状态标记 isDone，和一个响应结果 result，
 * 这个容器用来保存一些任务的响应结果。
 *
 * @author Gary
 * @param <T> 响应结果的类型
 */
public class AsyncFutureTask<T> implements Future<T> {

    /**
     * 标记任务是否执行完成，默认是 false。
     */
    private volatile boolean isDone;

    /**
     * 任务执行结束后返回的响应结果。
     */
    private T result;

    /**
     * 在任务执行后调用 set 方法，将结果保存到容器中
     *
     * @param result 任务执行结果
     */
    public synchronized void set(T result) {
        this.result = result;
        this.isDone = Boolean.TRUE;
        this.notifyAll();
    }

    /**
     * 获取容器内的值，即任务的执行结果。
     *
     * @return 任务的执行结果
     * @throws InterruptedException 中断异常
     */
    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!isDone) {
                this.wait();
            }
        }
        return result;
    }
}
