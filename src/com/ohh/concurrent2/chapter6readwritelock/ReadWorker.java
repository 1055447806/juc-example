package com.ohh.concurrent2.chapter6readwritelock;

/**
 * 读操作线程
 */
public class ReadWorker extends Thread {
    private final SharedData data;

    public ReadWorker(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] readbuf = data.read();
                System.out.println(Thread.currentThread().getName() + " read " + String.valueOf(readbuf));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
