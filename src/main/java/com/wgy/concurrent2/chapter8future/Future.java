package com.wgy.concurrent2.chapter8future;

/**
 * Future 接口，是 “任务执行结果” 的抽象
 *
 * @author Gary
 * @param <T> 结果的类型
 */
public interface Future<T> {
    /**
     * 通过调用 get 方法，可以获取执行后产生的的结果
     *
     * @return 执行后产生的的结果
     * @throws InterruptedException 中断异常
     */
    T get() throws InterruptedException;
}
