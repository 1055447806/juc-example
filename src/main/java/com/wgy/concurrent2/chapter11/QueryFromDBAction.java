package com.wgy.concurrent2.chapter11;

public class QueryFromDBAction {
    public void execute(Context context) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = "Gary " + Thread.currentThread().getName();
        context.setName(name);
    }
}
