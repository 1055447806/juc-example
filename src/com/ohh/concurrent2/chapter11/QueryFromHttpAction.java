package com.ohh.concurrent2.chapter11;

public class QueryFromHttpAction {
    public void execution(Context context) {
        String name = context.getName();
        String idCard = getIdCard(name);
        context.setIdCard(idCard);
    }

    private String getIdCard(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "123456123456123456 " + Thread.currentThread().getName();
    }
}
