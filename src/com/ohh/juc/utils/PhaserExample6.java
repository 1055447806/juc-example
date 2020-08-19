package com.ohh.juc.utils;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * arrive不会被blocked
 */
public class PhaserExample6 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(4);
        for (int i = 1; i < 4; i++) {
            new ArriveTask(phaser, i).start();
        }
        phaser.arriveAndAwaitAdvance();
        System.out.println("all the task one phase finished!");
    }

    private static class ArriveTask extends Thread {
        private final Phaser phaser;

        private ArriveTask(Phaser phaser, int no) {
            super(String.valueOf(no));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + ": start!");
            PhaserExample6.sleep(random.nextInt(5), TimeUnit.SECONDS);
            System.out.println(getName() + ": phase one end...");
            phaser.arrive();
            System.out.println(getName() + ": do other things...");
        }
    }

    private static void sleep(long l, TimeUnit t) {
        try {
            t.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
