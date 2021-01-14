package com.ohh.concurrent2.chapter8future;

/**
 * Callable 接口
 * Callable 是 “等待执行的任务” 的抽象
 *
 * @author Gary
 * @param <T> 返回值类型
 */
public interface Callable<T> {
    /**
     * 重写 call 方法，在 call 方法中加入代码逻辑，
     * 通常这些逻辑需要花费大量的时间，比如对大量数据的查询操作。
     *
     * @return 执行结果
     */
    T call();
}
