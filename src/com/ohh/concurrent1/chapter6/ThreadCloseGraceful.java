package com.ohh.concurrent1.chapter6;

public class ThreadCloseGraceful {

    private static class Worker extends Thread {
        // 定义一个开关
        private volatile boolean start = true;

        @Override
        public void run() {
            while (start) {

            }
        }

        private void shutdown() {
            this.start = false;
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.shutdown();
    }
}
