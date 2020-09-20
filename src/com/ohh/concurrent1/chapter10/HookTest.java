package com.ohh.concurrent1.chapter10;

public class HookTest {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("程序已退出!");
        }));
        int i = 0;
        while (true) {
            try {
                if (i++ >= 5) throw new RuntimeException("exit!!!");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
