package com.ohh.juc.utils.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerExample2 {
    public static void main(String[] args) {
        Exchanger<Object> exchanger = new Exchanger<>();
        new Thread(()->{
            Object object = new Object();
            System.out.println("T1: " + object);
            try {
                Object result = exchanger.exchange(object);
                System.out.println("T1 exchange result: " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            Object object = new Object();
            System.out.println("T2: " + object);
            try {
                Object result = exchanger.exchange(object);
                System.out.println("T2 exchange result: " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
