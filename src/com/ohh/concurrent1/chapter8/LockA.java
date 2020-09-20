package com.ohh.concurrent1.chapter8;

public class LockA {
    private LockB lockB;
    private final Object lock = new Object();

    public void setLockB(LockB lockB) {
        this.lockB = lockB;
    }

    public void a1() {
        synchronized (lock) {
            System.out.println("A1");
            lockB.b1();
        }
    }

    public void a2() {
        synchronized (lock) {
            System.out.println("A2");
        }
    }
}
