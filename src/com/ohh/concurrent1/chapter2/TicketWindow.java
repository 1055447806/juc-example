package com.ohh.concurrent1.chapter2;

public class TicketWindow extends Thread {

    private final int MAX = 50;     //总共有多少号码

    private static int index = 1;

    public TicketWindow(String name) {
        super(name);
    }

    public static int getIndex() {
        return index;
    }

    @Override
    public void run() {
        //模拟叫号过程
        while (index <= MAX) {
            System.out.println(Thread.currentThread().getName() + ": 当前的号码是" + index++);
        }
    }
}
