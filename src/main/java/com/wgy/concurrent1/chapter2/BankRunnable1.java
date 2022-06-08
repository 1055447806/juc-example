package com.wgy.concurrent1.chapter2;

/**
 * 通过模拟银行窗口叫号，实现线程通信，Runnable 接口的方式。
 */
public class BankRunnable1 {

    private static final int MAX = 50;

    public static void main(String[] args) {

        // 这种方式存在问题，index 是局部变量，没有被多个线程所共享，
        // 所以每个线程各自运行了 50 次。
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
