package com.wgy.concurrent1.chapter9;

import java.util.stream.Stream;

public class ProducerConsumerVersion2 {

    private int i = 0;
    private final Object LOCK = new Object();
    private volatile boolean isProduced = false;

    private void produce() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "->" + ++i);
            isProduced = true;
            LOCK.notify();
        }
    }

    private void consume() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "->" + i);
            isProduced = false;
            LOCK.notify();
        }
    }

    public static void main(String[] args) {

        ProducerConsumerVersion2 pc = new ProducerConsumerVersion2();

        Stream.of("P1", "P2").forEach(p ->
                new Thread(() -> {
                    while (true) {
                        pc.produce();
                    }
                }, p).start()
        );

        Stream.of("C1", "C2").forEach(c ->
                new Thread(() -> {
                    while (true) {
                        pc.consume();
                    }
                }, c).start()
        );
    }
}