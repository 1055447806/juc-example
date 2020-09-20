package com.ohh.concurrent1.chapter8;

public class LockB {
    private LockA lockA;
    private final Object lock = new Object();

    public void setLockA(LockA lockA) {
        this.lockA = lockA;
    }

    public void b1() {
        synchronized (lock) {
            System.out.println("B1");
        }
    }

    public void b2() {
        synchronized (lock) {
            System.out.println("B2");
            lockA.a2();
        }
    }
}
