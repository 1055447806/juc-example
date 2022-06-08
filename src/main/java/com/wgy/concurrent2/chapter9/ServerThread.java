package com.wgy.concurrent2.chapter9;

import java.util.Random;

public class ServerThread extends Thread {
    private final RequestQueue queue;
    private final Random random;
    private volatile boolean active = true;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (active) {
            Request request = queue.getRequest();
            if (request == null) {
                System.out.println("Received the empty request");
                continue;
            }
            System.out.println("Server -> " + request.getValue());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void close() {
        this.active = false;
        this.interrupt();
    }
}
