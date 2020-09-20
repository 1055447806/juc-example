package com.ohh.concurrent1.chapter7;

public class Main {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                SynchronizedStatic.m1();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                SynchronizedStatic.m2();
            }
        }.start();
    }
}
