package com.ohh.concurrent1.chapter8;

public class DeadLockTest {

    public static void main(String[] args) {
        LockA lockA = new LockA();
        LockB lockB = new LockB();
        lockB.setLockA(lockA);
        lockA.setLockB(lockB);

        new Thread(() -> {
            while (true) {
                lockA.a1();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                lockB.b2();
            }
        }).start();
    }
}