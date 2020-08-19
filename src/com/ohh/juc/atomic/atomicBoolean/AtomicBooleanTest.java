package com.ohh.juc.atomic.atomicBoolean;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtomicBooleanTest {
    @Test
    public void testCreateWithOutArgument() {
        AtomicBoolean bool = new AtomicBoolean();
        assertFalse(bool.get());
    }

    @Test
    public void testCreateWithArgument() {
        AtomicBoolean bool = new AtomicBoolean(true);
        assertTrue(bool.get());
    }

    @Test
    public void testGetAndSet() {
        AtomicBoolean bool = new AtomicBoolean(true);
        assertTrue(bool.getAndSet(false));
        assertFalse(bool.get());
    }

    @Test
    public void testCompareAndSet() throws InterruptedException {
        int count = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        AtomicBoolean bool = new AtomicBoolean(true);

        for (int i = 0; i < count; i++) {
            new Thread(()->{
                boolean prev = bool.get();
                if (!bool.compareAndSet(prev, !prev)){
                    System.out.println("compareAndSet方法失败！");
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println("共取反"+count+"次，结果：bool = " + bool);
    }
}
