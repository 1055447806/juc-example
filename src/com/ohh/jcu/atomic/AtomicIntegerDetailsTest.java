package com.ohh.jcu.atomic;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDetailsTest {

    @Test
    public void testCreate() {
        AtomicInteger i = new AtomicInteger();
        System.out.println(i.get());
        i = new AtomicInteger(10);
        System.out.println(i.get());
    }

    @Test
    public void testSet() {
        AtomicInteger i = new AtomicInteger();
        i.set(12);
        System.out.println(i.get());
        i.lazySet(14);
        System.out.println(i.get());
    }

    /**
     * 单线程
     */
    @Test
    public void testGetAndAdd() {
        AtomicInteger i = new AtomicInteger();
        i.set(10);
        System.out.println(i.getAndAdd(10));
        System.out.println(i.get());
    }

    /**
     * 多线程
     * @throws InterruptedException
     */
    @Test
    public void testGetAndAdd2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        AtomicInteger value = new AtomicInteger(0);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    int result = value.getAndAdd(1);
                    System.out.println(result);
                }
                countDownLatch.countDown();
            }
        };
        for (int i = 0; i < 5; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
    }

    @Test
    public void test() {
        AtomicInteger i = new AtomicInteger(10);
        final boolean flag = i.compareAndSet(12, 20);
        System.out.println("flag = " + flag);
        System.out.println("i.get() = " + i.get());
    }

}
