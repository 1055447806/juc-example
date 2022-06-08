package com.wgy.juc.utils.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample1 {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static int data = 0;
    private static volatile boolean noUse = true;

    private static void buildData() {
        try {
            lock.lock();
            while (noUse) {
                condition.await();
            }
            data++;
            noUse = true;
            System.out.println("product data: " + data);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void useData() {
        try {
            lock.lock();
            while (!noUse) {
                condition.await();
            }
            System.out.println("consume data: " + data);
            noUse = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
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
