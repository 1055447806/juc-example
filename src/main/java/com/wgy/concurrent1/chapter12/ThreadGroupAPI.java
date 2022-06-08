package com.wgy.concurrent1.chapter12;

import java.util.Arrays;

public class ThreadGroupAPI {
    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
        Thread t2 = new Thread(tg2, "t2") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        t2.start();

        System.out.println(tg1.activeCount());
        System.out.println(tg1.activeGroupCount());
        t2.checkAccess();
//        tg1.destroy();
        System.out.println("==============");
        Thread[] ts1 = new Thread[tg1.activeCount()];
        tg1.enumerate(ts1);
        Arrays.asList(ts1).forEach(System.out::println);
        System.out.println("==============");
        ts1 = new Thread[10];
        Thread.currentThread().getThreadGroup().enumerate(ts1, false);
        Arrays.asList(ts1).forEach(System.out::println);
        System.out.println("==============");
        tg1.interrupt();
        System.out.println("tg1: " + tg1.isDestroyed());
        System.out.println("==============");

        ThreadGroup tg3 = new ThreadGroup("TG3");
        Thread t3 = new Thread(tg3, "t3") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t3.start();
        tg3.setDaemon(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("tg3: " + tg3.isDestroyed());
    }
}
