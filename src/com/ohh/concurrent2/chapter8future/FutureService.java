package com.ohh.concurrent2.chapter8future;

import java.util.function.Consumer;

/**
 * FutureService 使任务实现了 Future 设计模式，
 * 可以通过创建并使用 FutureService 对象执行任务，使任务的执行异步化，
 * 它包含了一个 submit 方法，这个方法是任务以 Future 设计模式执行的入口。
 *
 * @author Gary
 */
public class FutureService {

    /**
     * 通过 submit 方法提交一个 Callable，此时会创建一个与之绑定的 AsyncFutureTask，
     * 通过启动一个新的线程去执行 call 方法，当 call 执行结束后，通过调用 future 的 set 方法，
     * 将结果保存在 future 中，最会把保存了执行结果的 future 返回。
     *
     * @param callable 被执行的任务
     * @param <T>      执行结果的类型
     * @return 存有执行结果的 future
     */
    public <T> Future<T> submit(final Callable<T> callable) {
        AsyncFutureTask<T> asyncFutureTask = new AsyncFutureTask<>();
        new Thread(() -> asyncFutureTask.set(callable.call())).start();
        return asyncFutureTask;
    }

    /**
     * 通过 submit 方法提交一个 Callable 的同时传入一个 Consumer，
     * 此 Consumer 用来执行回调函数，在某些情况下可以不用显示的调用 get 方法获取返回值，
     * 比如直接对结果进行输出，可以传入一个 “System.out::println” 方法引用。
     *
     * @param callable 被执行的任务
     * @param consumer 回调函数
     * @param <T>      执行结果的类型
     * @return 存有执行结果的 future
     */
    public <T> Future<T> submit(final Callable<T> callable, final Consumer<T> consumer) {
        AsyncFutureTask<T> asyncFutureTask = new AsyncFutureTask<>();
        new Thread(() -> {
            T result = callable.call();
            consumer.accept(result);
            asyncFutureTask.set(result);
        }).start();
        return asyncFutureTask;
    }
}
