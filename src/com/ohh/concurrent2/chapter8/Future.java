package com.ohh.concurrent2.chapter8;

public interface Future<T> {
    T get() throws InterruptedException;
}
