package com.wgy.concurrent1.chapter2;

/**
 * 通过模拟银行窗口叫号，实现线程通信，Runnable 接口的方式。
 */
public class BankRunnable2 {

    /**
     * 通过实现 Runnable 接口的自定义类 MyRunnable，实现了线程间的共享变量。
     */
    public static void main(String[] args) {
        Thread windowThread1 = new Thread(new MyRunnable(), "1号窗口");
        Thread windowThread2 = new Thread(new MyRunnable(), "2号窗口");
        Thread windowThread3 = new Thread(new MyRunnable(), "3号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }


    private static class MyRunnable implements Runnable {

        private final int MAX = 50;

        private int index = 1;

        //这种方式仍然存在线程安全问题。
        @Override
        public void run() {
            while (index <= MAX) {
                System.out.println(Thread.currentThread().getName() + ": 当前的号码是" + index++);
            }
        }
    }
}
