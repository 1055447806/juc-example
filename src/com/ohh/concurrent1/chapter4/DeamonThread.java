package com.ohh.concurrent1.chapter4;

/**
 * 守护线程的使用。
 */
public class DeamonThread {

    public static void main(String[] args) throws InterruptedException {

        Thread subThread = new Thread(() -> {
            try {
                //running
                System.out.println("subThread is running.");
                Thread.sleep(10_000);//timed_waiting
                System.out.println("subThread done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });//此时为new状态

        //在 main 线程中设置子线程为守护线程，当 main 线程结束时，守护线程也会结束。
        subThread.setDaemon(true);
        subThread.start(); //此时为runnable状态

        //让 main 线程 3 秒后结束。
        Thread.sleep(3_000);
        System.out.println("main finished.");
    }
}
