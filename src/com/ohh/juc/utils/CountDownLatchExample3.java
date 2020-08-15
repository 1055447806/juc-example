package com.ohh.juc.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample3 {
    public static void main(String[] args) throws InterruptedException {
        final Thread mainThread = Thread.currentThread();
        /**
         * CountDownLatch的参数为0的时候await不住，为负数的时候会抛异常
         */
//        final CountDownLatch countDownLatch = new CountDownLatch(0);
//        final CountDownLatch countDownLatch = new CountDownLatch(-1);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(()->{
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            mainThread.interrupt();
            countDownLatch.countDown();
        }).start();
        countDownLatch.await(5_000, TimeUnit.MILLISECONDS);
        System.out.println("done...");
    }
}
