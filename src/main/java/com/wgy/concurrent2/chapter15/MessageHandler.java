package com.wgy.concurrent2.chapter15;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageHandler {
    private static final Executor executor = Executors.newFixedThreadPool(5);
    private static final Random random = new Random(System.currentTimeMillis());

    public void request(Message message) {
        executor.execute(() -> {
            String value = message.getValue();
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The message will be handle by " + Thread.currentThread().getName() + " " + value);
        });
       /* new Thread(()->{
            String value = message.getValue();
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The message will be handle by "+Thread.currentThread().getName()+value);
        });*/
    }

    public void shutdown() {
        ((ExecutorService) executor).shutdown();
    }
}
