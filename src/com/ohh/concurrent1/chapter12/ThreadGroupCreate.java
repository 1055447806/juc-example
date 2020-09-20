package com.ohh.concurrent1.chapter12;

import java.util.Arrays;

public class ThreadGroupCreate {
    public static void main(String[] args) {

        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                // t1线程访问自己的线程组信息
                System.out.println("在t1中访问tg1: " + getThreadGroup().getName());
                // t1线程访问自己的线程组的父线程组信息
                System.out.println("在t1中访问tg1的父线程组: " + getThreadGroup().getParent().getName());
                // t1线程访问自己的线程组的父线程组信息
                System.out.println("在t1中访问tg1的父线程组的活动线程数: " + getThreadGroup().getParent().activeCount());
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup("TG2");
        Thread t2 = new Thread(tg2, "t2") {
            @Override
            public void run() {
                // 在t2线程中访问tg1线程组的信息, 并将tg1线程组中的活动线程打印到控制台
                System.out.println("在t2中访问tg1: " + tg1.getName());
                Thread[] threads = new Thread[tg1.activeCount()];
                tg1.enumerate(threads);
                Arrays.asList(threads).forEach(System.out::println);
            }
        };
        t2.start();

        System.out.println("在main线程中访问tg2: " + tg2.getName());
        System.out.println("在main线程中访问tg2的父线程组: " + tg2.getParent().getName());
    }
}