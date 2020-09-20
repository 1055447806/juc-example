package com.ohh.concurrent1.chapter4;

public class DeamonThread {

    public static void main(String[] args) {

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    //running
                    System.out.println(Thread.currentThread().getName() + " is running.");
                    Thread.sleep(100_000);//timed_waiting
                    System.out.println(Thread.currentThread().getName() + " done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };//此时为new状态

        t.setDaemon(true);
        t.start(); //此时为runnable状态

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName());
    }
}
