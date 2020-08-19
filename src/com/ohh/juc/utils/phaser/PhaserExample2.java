package com.ohh.juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 非动态的
 */
public class PhaserExample2 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 1; i < 6; i++) {
            new Athletes(i, phaser).start();
        }
    }

    static class Athletes extends Thread {
        private final int no;
        private final Phaser phaser;

        Athletes(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(no + "start running...");
            PhaserExample2.sleep(random.nextInt(5), TimeUnit.SECONDS);
            System.out.println(no + "end running...");
            System.out.println("this.phaser.getPhase() = " + this.phaser.getPhase());
            this.phaser.arriveAndAwaitAdvance();

            System.out.println(no + "start bicycle...");
            PhaserExample2.sleep(random.nextInt(5), TimeUnit.SECONDS);
            System.out.println(no + "end bicycle...");
            System.out.println("this.phaser.getPhase() = " + this.phaser.getPhase());
            this.phaser.arriveAndAwaitAdvance();

            System.out.println(no + "start long jump...");
            PhaserExample2.sleep(random.nextInt(5), TimeUnit.SECONDS);
            System.out.println(no + "end long jump...");
            System.out.println("this.phaser.getPhase() = " + this.phaser.getPhase());
            this.phaser.arriveAndAwaitAdvance();
            System.out.println("this.phaser.getPhase() = " + this.phaser.getPhase());
        }
    }

    private static void sleep(long i, TimeUnit t) {
        try {
            t.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
