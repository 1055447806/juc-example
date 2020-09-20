package com.ohh.concurrent1.chapter3;

public class CreateThread {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            SyncStatic.m1();
        }, "t1").start();
        new Thread(() -> {
            SyncStatic.m2();
        }, "t2").start();
        new Thread(() -> {
            SyncStatic.m3();
        }, "t3").start();
    }

}
