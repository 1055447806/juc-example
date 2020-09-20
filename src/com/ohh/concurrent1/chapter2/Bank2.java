package com.ohh.concurrent1.chapter2;

public class Bank2 {

    private static final int MAX = 50;

    public static void main(String[] args) {

        final Runnable ticketWindowRunnable = () -> {
            int index = 1;
            while (index <= MAX) {
                System.out.println(Thread.currentThread().getName() + ": 当前的号码是" + index++);
            }
        };

        Thread windowThread1 = new Thread(ticketWindowRunnable, "1号窗口");
        Thread windowThread2 = new Thread(ticketWindowRunnable, "2号窗口");
        Thread windowThread3 = new Thread(ticketWindowRunnable, "3号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }
}
