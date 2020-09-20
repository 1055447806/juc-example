package com.ohh.concurrent2.chapter11_2;

public class QueryFromDBAction {
    public void execute() {
        try {
            Thread.sleep(1000L);
            String name = "Gary " + Thread.currentThread().getName();
            ActionContext.getInstance().getContext().setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
