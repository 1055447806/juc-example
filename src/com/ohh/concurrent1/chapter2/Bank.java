package com.ohh.concurrent1.chapter2;

public class Bank {
    public static void main(String[] args) {
        TicketWindow ticketWindow1 = new TicketWindow("柜台1");
        TicketWindow ticketWindow2 = new TicketWindow("柜台2");
        TicketWindow ticketWindow3 = new TicketWindow("柜台3");
        ticketWindow1.start();
        ticketWindow2.start();
        ticketWindow3.start();
        try {
            Thread.sleep(1000 * 500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
