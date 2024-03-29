package com.wgy.concurrent2.chapter14;

import java.util.Random;
import java.util.stream.IntStream;

public class CustomCountDownClient {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {

        final CountDown latch = new CountDown(5);

        System.out.println("准备多线程处理任务");
        //The first phase
        IntStream.rangeClosed(1, 5).forEach(i -> new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is working.");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.down();
        }, String.valueOf(i)).start());

        latch.await();

        System.out.println("多线程任务结束，准备第二阶段任务");
        //The second phase
    }
}
