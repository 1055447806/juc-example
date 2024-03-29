package com.wgy.juc.utils.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample1 {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{
            System.out.println("all of finished.");
        });
        new Thread (()->{
            try {
                TimeUnit.SECONDS.sleep(20);
                System.out.println("T1 finished.");
                cyclicBarrier.await();
                System.out.println("T1: The other thread finished too.");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread (()->{
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("T2 finished.");
                cyclicBarrier.await();
                System.out.println("T2: The other thread finished too.");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        while (true) {
            System.out.println(cyclicBarrier.getNumberWaiting());
            System.out.println(cyclicBarrier.getParties());
            System.out.println(cyclicBarrier.isBroken());
            System.out.println();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
