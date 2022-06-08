package com.wgy.concurrent1.chapter6;

public class ThreadCloseGraceful2 {

    private static class Worker extends Thread {
        //通过中断的方式
        @Override
        public void run() {
            while (true) {
                /*try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }*/
                if (Thread.interrupted()) break;
            }
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

        worker.interrupt();
    }
}
