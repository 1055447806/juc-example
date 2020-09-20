package com.ohh.concurrent1.chapter3;

public class SyncStatic {

    public synchronized static void m1() {
        System.out.println(Thread.currentThread().getName() + " run " + " m1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m2() {
        System.out.println(Thread.currentThread().getName() + " run " + " m2");
    }

    public static void m3() {
        System.out.println(Thread.currentThread().getName() + " run " + " m3");
    }
}
