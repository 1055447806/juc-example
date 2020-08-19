package com.ohh.juc.utils.condition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample3 {
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition PRODUCE_CONDITION = LOCK.newCondition();
    private static final Condition CONSUME_CONDITION = LOCK.newCondition();
    private static final LinkedList<Long> TIMESTAMP_POOL = new LinkedList<>();
    private static final int MAX_CAPACITY = 100;

    public static void main(String[] args) {
        beginProduce(10);
        beginConsume(10);
    }

    private static void produce() {
        try {
            LOCK.lock();
            while (TIMESTAMP_POOL.size() >= MAX_CAPACITY) {
                PRODUCE_CONDITION.await();
            }
            Long value = System.currentTimeMillis();
            TIMESTAMP_POOL.addLast(value);
            System.out.println(Thread.currentThread().getName() + " p " + value + " size: " + TIMESTAMP_POOL.size());
            CONSUME_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void consume() {
        try {
            LOCK.lock();
            while (TIMESTAMP_POOL.isEmpty()) {
                CONSUME_CONDITION.await();
            }
            Long value = TIMESTAMP_POOL.removeFirst();
            System.out.println(Thread.currentThread().getName() + " c " + value + " size: " + TIMESTAMP_POOL.size());
            PRODUCE_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void beginProduce(int i) {
        for (int j = 0; j < i; j++) {
            new Thread(() -> {
                while (true) {
                    produce();
                }
            }, "P" + j).start();
        }
    }

    private static void beginConsume(int i) {
        for (int j = 0; j < i; j++) {
            new Thread(() -> {
                while (true) {
                    consume();
                }
            }, "C" + j).start();
        }
    }
}
