package com.ohh.concurrent1.chapter7;

public class TicketWindowRunnable implements Runnable {

    private static final int MAX = 500;
    private int index = 1;
    private static final Object MONITOR = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (MONITOR) {
                if (index > MAX) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ": 当前的号码是" + index++);

            }
        }
    }
}
