package com.ohh.juc.utils.condition;

public class ConditionExample2 {
    private static int data = 0;
    private static volatile boolean noUse = true;

    private static void buildData() {
        synchronized (ConditionExample2.class) {
            while (noUse) {
                try {
                    ConditionExample2.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data++;
            System.out.println("product data: " + data);
            noUse = true;
            ConditionExample2.class.notifyAll();
        }
    }

    private static void useData() {
        synchronized (ConditionExample2.class) {
            while (!noUse) {
                try {
                    ConditionExample2.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("consume data: " + data);
            noUse = false;
            ConditionExample2.class.notifyAll();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                buildData();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                buildData();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                useData();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                useData();
            }
        }).start();
    }
}
