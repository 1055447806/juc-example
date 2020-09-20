package com.ohh.concurrent2.chapter8;

public class FutureService {
    public <T> AsynFuture<T> submit(final FutureTask<T> futureTask) {
        AsynFuture<T> asynFuture = new AsynFuture<T>();
        new Thread(() -> {
            T result = futureTask.call();
            asynFuture.done(result);
        }).start();
        return asynFuture;
    }
}
