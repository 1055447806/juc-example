package com.wgy.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class PhaserExample8 {

    /**
     * arriveAndAwaitAdvance不会被中断
     */
    /*public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(3);
        Thread thread = new Thread(phaser::arriveAndAwaitAdvance);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println("interrupt!");
    }*/

    /**
     * awaitAdvanceInterruptibly可以被中断
     */
    /*public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(3);
        Thread thread = new Thread(()->{
            try {
                phaser.awaitAdvanceInterruptibly(0);
                System.out.println(Thread.currentThread().getName()+": await failed...");
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName()+": i was interrupted!......");
                e.printStackTrace();
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println("interrupt!");
    }*/

    /**
     * awaitAdvanceInterruptibly 超时或者被中断都会停止等待
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(3);
        Thread thread = new Thread(() -> {
            try {
                phaser.awaitAdvanceInterruptibly(0, 3, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + ": await failed...");
            } catch (InterruptedException | TimeoutException e) {
                System.err.println(Thread.currentThread().getName() + ": i don't keep wait!......");
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
