package com.wgy.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * awaitAdvance不会修改arrived的数量
 */
public class PhaserExample7 {

    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(7);
        /*new Thread(() -> phaser.awaitAdvance(phaser.getPhase())).start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(phaser.getArrivedParties());*/
        for (int i = 0; i < 6; i++) {
            new AwaitAdviceTask(phaser).start();
        }
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("all task finished!");
    }

    private static class AwaitAdviceTask extends Thread {
        private final Phaser phaser;

        private AwaitAdviceTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + ": finished...");
        }
    }
}
