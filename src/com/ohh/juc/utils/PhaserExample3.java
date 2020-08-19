package com.ohh.juc.utils;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 动态的消减
 */
public class PhaserExample3 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        int i;
        for (i = 1; i < 5; i++) {
            new Athletes(i, phaser).start();
        }
        new InjuredAthletes(i, phaser).start();
    }

    /**
     * 运动员
     */
    static class Athletes extends Thread {
        private final int no;
        private final Phaser phaser;

        Athletes(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            sport(no, phaser, "start running...", "end running...");
            sport(no, phaser, "start bicycle...", "end bicycle...");
            sport(no, phaser, "start long jump...", "end long jump...");
        }
    }

    /**
     * 受伤的运动员
     */
    static class InjuredAthletes extends Thread {
        private final int no;
        private final Phaser phaser;

        InjuredAthletes(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            sport(no, phaser, "start running...", "end running...");
            sport(no, phaser, "start bicycle...", "end bicycle...");
            System.out.println("Oh shit! i am injured! i will be exited...");
            phaser.arriveAndDeregister();
        }
    }

    private static void sport(int no, Phaser phaser, String s, String s2) {
        System.out.println(no + s);
        PhaserExample3.sleep(random.nextInt(5), TimeUnit.SECONDS);
        System.out.println(no + s2);
        phaser.arriveAndAwaitAdvance();
    }

    private static void sleep(long i, TimeUnit t) {
        try {
            t.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
