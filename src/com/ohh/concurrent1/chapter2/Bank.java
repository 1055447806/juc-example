package com.ohh.concurrent1.chapter2;

/**
 * 通过模拟银行窗口叫号，实现线程通信，继承 Thread 类的方式。
 */
public class Bank {

    public static void main(String[] args) {
        TicketWindow ticketWindow1 = new TicketWindow("柜台1");
        TicketWindow ticketWindow2 = new TicketWindow("柜台2");
        TicketWindow ticketWindow3 = new TicketWindow("柜台3");
        ticketWindow1.start();
        ticketWindow2.start();
        ticketWindow3.start();
    }

    private static class TicketWindow extends Thread {

        private final int MAX = 100;     //总共有多少号码

        private static int index = 1;

        public TicketWindow(String name) {
            super(name);
        }

        @Override
        public void run() {
            //模拟叫号过程，这里存在线程安全问题。
            while (index <= MAX) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": 当前的号码是" + index++);
            }
        }
    }
}
