package com.ohh.juc.utils;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 当onAdvance返回true，第一次仍然有效，但是后面无效
 */
public class PhaserExample5 {

    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(2) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return true;
            }
        };
        new OnAdvanceTask("T1", phaser).start();
        new OnAdvanceTask("T2", phaser).start();
        TimeUnit.SECONDS.sleep(4);
        System.out.println("phaser.getArrivedParties() = " + phaser.getArrivedParties());
        System.out.println("phaser.getUnarrivedParties() = " + phaser.getUnarrivedParties());
    }

    static class OnAdvanceTask extends Thread {
        private final Phaser phaser;

        OnAdvanceTask(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + ": first time : phaser is terminated: " + phaser.isTerminated());
            System.out.println(getName() + ": start. current phase: " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + ": end...");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(getName() + ": second time : phaser is terminated: " + phaser.isTerminated());
            if (getName().equals("T1")) {
                System.out.println(getName() + ": start. current phase: " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
                System.out.println(getName() + ": end...");
            }
        }
    }
}
