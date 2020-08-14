package com.ohh.juc.atomic;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class AtomicIntegerDetailsTest2 {

    @Test
    public void testSynchronized() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                try {
                    doSomeThing();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
    }

    /**
     * 使用 synchronized
     * @throws InterruptedException
     */
    public static void doSomeThing() throws InterruptedException {
        synchronized (AtomicIntegerDetailsTest2.class) {
            System.out.println(Thread.currentThread().getName() + " get the lock.");
            Thread.sleep(100_000L);
        }
    }

    @Test
    public void testCompareAndSetLock() throws InterruptedException {
        CompareAndSetLock compareAndSetLock = new CompareAndSetLock();
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(()->{
                try {
                    doSomeThing2(compareAndSetLock);
                } catch (InterruptedException | GetLockException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
    }

    /**
     * 使用 CAS
     * @param compareAndSetLock
     * @throws InterruptedException
     * @throws GetLockException
     */
    public static void doSomeThing2(CompareAndSetLock compareAndSetLock) throws InterruptedException, GetLockException {
        try{
            compareAndSetLock.tryLock();
            System.out.println(Thread.currentThread().getName() + " get the lock.");
            Thread.sleep(100_000L);
        }finally {
            compareAndSetLock.unLock();
        }
    }

}
