package com.ohh.concurrent2.chapter6readwritelock;

import java.util.Random;

/**
 * 写操作线程
 */
public class WriteWorker extends Thread {

    private static final Random random = new Random(System.currentTimeMillis());
    private final SharedData data;
    private final String filler; // 修改数据时使用的值
    private int index = 0;

    public WriteWorker(SharedData data, String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = nextChar();
                data.write(c);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public char nextChar() {
        char c = filler.charAt(index);
        index++;
        if (index >= filler.length()) {
            index = 0;
        }
        return c;
    }
}
