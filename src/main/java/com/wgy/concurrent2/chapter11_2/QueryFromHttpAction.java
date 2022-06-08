package com.wgy.concurrent2.chapter11_2;

public class QueryFromHttpAction {

    private static final Context context = ActionContext.getInstance().getContext();

    public void execution() {
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
