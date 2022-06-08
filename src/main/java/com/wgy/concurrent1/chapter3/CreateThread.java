package com.wgy.concurrent1.chapter3;

/**
 * synchronized 关键字的简单示例。
 */
public class CreateThread {

    public static void main(String[] args) {
        new Thread(SyncStatic::m1, "t1").start();
        new Thread(SyncStatic::m2, "t2").start();
        new Thread(SyncStatic::m3, "t3").start();
    }

    private static class SyncStatic {

        public synchronized static void m1() {
            System.out.println(Thread.currentThread().getName() + " run m1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized static void m2() {
            System.out.println(Thread.currentThread().getName() + " run m2");
        }

        public static void m3() {
            System.out.println(Thread.currentThread().getName() + " run m3");
        }
    }
}
