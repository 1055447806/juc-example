package com.ohh.juc.utils.phaser;

import java.util.concurrent.Phaser;

public class PhaserExample4 {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(1);
        /*for (int i = 0; i < 10; i++) {
            System.out.println("phaser.getPhase() = " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
        }*/
        /*for (int i = 0; i < 10; i++) {
            System.out.println("phaser.getRegisteredParties() = " + phaser.getRegisteredParties());
            phaser.register();
        }*/
        /*System.out.println("phaser.getArrivedParties() = " + phaser.getArrivedParties());
        System.out.println("phaser.getUnarrivedParties() = " + phaser.getUnarrivedParties());*/
        /*phaser.bulkRegister(10);
        System.out.println("phaser.getRegisteredParties() = " + phaser.getRegisteredParties());
        System.out.println("phaser.getArrivedParties() = " + phaser.getArrivedParties());
        System.out.println("phaser.getUnarrivedParties() = " + phaser.getUnarrivedParties());
        new Thread(phaser::arriveAndAwaitAdvance).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("phaser.getRegisteredParties() = " + phaser.getRegisteredParties());
        System.out.println("phaser.getArrivedParties() = " + phaser.getArrivedParties());
        System.out.println("phaser.getUnarrivedParties() = " + phaser.getUnarrivedParties());*/
    }

}
