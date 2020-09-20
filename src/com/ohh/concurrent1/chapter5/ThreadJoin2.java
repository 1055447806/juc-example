package com.ohh.concurrent1.chapter5;

public class ThreadJoin2 {

    public static void main(String[] args) {

        //服务的线程
        //...

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
