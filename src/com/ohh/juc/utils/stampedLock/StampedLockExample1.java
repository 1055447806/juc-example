package com.ohh.juc.utils.stampedLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

public class StampedLockExample1 {
    private static final StampedLock LOCK = new StampedLock();
    private static final List<Long> DATA = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            while (true) {
                read();
            }
        };
        Runnable writeTask = () -> {
            while (true) {
                write();
            }
        };
        for (int i = 0; i < 9; i++) {
            executor.submit(readTask);
        }
        executor.submit(writeTask);
    }

    private static void read() {
        long stamp = -1;
        try {
            stamp = LOCK.readLock();
            String collect = DATA.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""));
            TimeUnit.SECONDS.sleep(1);
            System.out.println(collect);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockRead(stamp);
        }
    }

    private static void write() {
        long stamp = -1;
        try {
            stamp = LOCK.writeLock();
            TimeUnit.SECONDS.sleep(1);
            DATA.add(System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockWrite(stamp);
        }
    }
}
