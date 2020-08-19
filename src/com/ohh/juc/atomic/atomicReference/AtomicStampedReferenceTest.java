package com.ohh.juc.atomic.atomicReference;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {

    /**
     * ABA问题
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 0);

        // 100 => 101
        new Thread(() -> {
            Integer reference = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            System.out.println("reference = " + reference + ", stamp = " + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean success = atomicStampedReference.compareAndSet(reference, reference + 1, stamp, stamp + 1);
            System.out.println("success = " + success);
            reference = atomicStampedReference.getReference();
            stamp = atomicStampedReference.getStamp();
            System.out.println("reference = " + reference + ", stamp = " + stamp);
            countDownLatch.countDown();
        }).start();

        // 100 => 101 => 100
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer reference = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet(reference, reference + 1, stamp, stamp + 1);
            reference = atomicStampedReference.getReference();
            stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet(reference, reference - 1, stamp, stamp + 1);
            countDownLatch.countDown();
        }).start();

        countDownLatch.await();
    }

    /**
     * 测试compareAndSet()方法预期的“初值”和想要更改的“结果值”相同
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        int count = 100;
        AtomicInteger failedCounter = new AtomicInteger(0);
        AtomicInteger threadCounter = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(count);

        AtomicStampedReference<Integer> reference = new AtomicStampedReference<>(1, 1);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                Integer ref = reference.getReference();
                int stamp = reference.getStamp();
                if (!reference.compareAndSet(ref, 1, stamp, 1)) {
                    failedCounter.incrementAndGet();
                }
                threadCounter.incrementAndGet();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("count is :" + count);
        System.out.println("threadCounter = " + threadCounter);
        System.out.println("failedCounter = " + failedCounter);
        System.out.println("reference.getReference() = " + reference.getReference());
        System.out.println("reference.getStamp() = " + reference.getStamp());
    }

}
