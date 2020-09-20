package com.ohh.concurrent2.chapter11;

public class ExecutionTask implements Runnable {
    private QueryFromDBAction queryFromDBAction = new QueryFromDBAction();
    private QueryFromHttpAction queryFromHttpAction = new QueryFromHttpAction();

    @Override
    public void run() {
        final Context context = new Context();
        queryFromDBAction.execute(context);
        System.out.println("The name query successful");
        queryFromHttpAction.execution(context);
        System.out.println("The idCard query successful");
        System.out.println("The name is " + context.getName() + " and idCard is " + context.getIdCard());
    }
}
