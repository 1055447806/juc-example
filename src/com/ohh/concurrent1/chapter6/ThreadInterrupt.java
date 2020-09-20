package com.ohh.concurrent1.chapter6;

public class ThreadInterrupt {

    public static void main(String[] args) {

        // 获取main线程
        final Thread main = Thread.currentThread();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("t1线程启动, 准备加入main线程");
                while (true) {

                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("t2线程启动, 3秒后打断main线程");
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("main中断状态: " + main.isInterrupted());
                System.out.println("t2线程开始打断main线程");
                main.interrupt();
            }
        };

        t1.start();

        // 延时100毫秒
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();

        // 延时100毫秒
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("t1线程开始加入main线程");
            t1.join();
        } catch (InterruptedException e) {
            System.out.println("main线程被打断了");
            System.out.println("main中断状态: " + Thread.currentThread().isInterrupted());
            e.printStackTrace();
        }

    }
}
