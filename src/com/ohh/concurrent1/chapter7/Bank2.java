package com.ohh.concurrent1.chapter7;

public class Bank2 {

    public static void main(String[] args) {

        final TicketWindowRunnable ticketWindow = new TicketWindowRunnable();

        Thread windowThread1 = new Thread(ticketWindow, "1号窗口");
        Thread windowThread2 = new Thread(ticketWindow, "2号窗口");
        Thread windowThread3 = new Thread(ticketWindow, "3号窗口");

        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }
}
