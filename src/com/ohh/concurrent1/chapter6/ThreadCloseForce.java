package com.ohh.concurrent1.chapter6;

public class ThreadCloseForce {
    public static void main(String[] args) {
        // 程序开始的时间
        long startTime = System.currentTimeMillis();

        ThreadService threadService = new ThreadService();
        threadService.execute(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadService.shutdown(3_000);

        // 程序结束的时间
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间: " + (endTime - startTime));
    }
}
