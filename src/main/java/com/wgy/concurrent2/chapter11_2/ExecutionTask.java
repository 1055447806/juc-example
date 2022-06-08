package com.wgy.concurrent2.chapter11_2;

public class ExecutionTask implements Runnable {
    private QueryFromDBAction queryFromDBAction = new QueryFromDBAction();
    private QueryFromHttpAction queryFromHttpAction = new QueryFromHttpAction();

    @Override
    public void run() {
        queryFromDBAction.execute();
        System.out.println("The name query successful");
        queryFromHttpAction.execution();
        System.out.println("The idCard query successful");
        Context context = ActionContext.getInstance().getContext();
        System.out.println("The name is " + context.getName() + " and idCard is " + context.getIdCard());
    }
}
