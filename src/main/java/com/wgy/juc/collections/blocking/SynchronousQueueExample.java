package com.wgy.juc.collections.blocking;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueExample {

    public <T> SynchronousQueue<T> create() {
        return new SynchronousQueue<>();
    }

    /**
     * 如果没有接收的线程，那么 add 方法会抛出异常
     */
    @Test
    public void testAdd() throws InterruptedException {
        SynchronousQueue<String> queue = create();

        // 启动一个接收元素的线程
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("queue.add(\"Hello\") = " + queue.add("Hello"));
    }

    /**
     * 如果没有接收的线程，那么 offer 方法会返回 false
     */
    @Test
    public void testOffer() throws InterruptedException {
        SynchronousQueue<String> queue = create();

        // 启动一个接收元素的线程
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("queue.add(\"Hello\") = " + queue.offer("Hello"));
    }

    /**
     * 如果没有接收的线程，那么 put 方法会阻塞
     */
    @Test
    public void testPut() throws InterruptedException {
        SynchronousQueue<String> queue = create();

        // 启动一个接收元素的线程
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        TimeUnit.SECONDS.sleep(1);
        queue.put("Hello");
    }


}
