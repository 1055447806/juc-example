package com.ohh.juc.utils;

import java.util.concurrent.Exchanger;

public class ExchangerExample1 {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " is start.");
            try {
                String result = exchanger.exchange("I come from " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " get value: " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end.");
        },"T1").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " is start.");
            try {
                String result = exchanger.exchange("I come from " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " get value: " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end.");
        },"T2").start();
    }
}
