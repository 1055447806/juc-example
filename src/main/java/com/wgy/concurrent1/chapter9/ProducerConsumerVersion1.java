package com.wgy.concurrent1.chapter9;

public class ProducerConsumerVersion1 {

    private int i = 1;
    private final Object LOCK = new Object();

    private void produce() {
        synchronized (LOCK) {
            System.out.println("P->" + i++);
        }
    }

    private void consume() {
        synchronized (LOCK) {
            System.out.println("C->" + i);
        }
    }

    public static void main(String[] args) {

        ProducerConsumerVersion1 pc = new ProducerConsumerVersion1();

        new Thread(() -> {
            while (true) {
                pc.produce();
            }
        }, "P").start();

        new Thread(() -> {
            while (true) {
                pc.consume();
            }
        }, "C").start();
    }
}
