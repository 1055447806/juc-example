package com.wgy.concurrent2.chapter6readwritelock;

public class Main {
    public static void main(String[] args) {
        final SharedData sharedData = new SharedData(10);
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new WriteWorker(sharedData, "qwertyuiop").start();
        new WriteWorker(sharedData, "QWERTYUIOP").start();
    }
}
