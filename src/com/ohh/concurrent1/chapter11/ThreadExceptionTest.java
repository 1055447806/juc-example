package com.ohh.concurrent1.chapter11;

import java.util.Arrays;

public class ThreadExceptionTest {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000);
                int i = 1 / 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println(thread);
            System.out.println(e);
        });
        t.start();
        Arrays.asList(t.getStackTrace()).forEach(s -> {
            System.out.println(s.getClassName() + ":" + s.getMethodName() + ":" + s.getLineNumber());
        });
    }
}
