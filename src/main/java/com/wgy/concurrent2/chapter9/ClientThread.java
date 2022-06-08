package com.wgy.concurrent2.chapter9;

import java.util.Random;

public class ClientThread extends Thread {
    private final RequestQueue queue;
    private final Random random;
    private final String value;

    public ClientThread(RequestQueue queue, String value) {
        this.queue = queue;
        this.random = new Random(System.currentTimeMillis());
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println("Client -> request " + value + ":" + i);
            queue.putRequest(new Request(value + ":" + i));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
