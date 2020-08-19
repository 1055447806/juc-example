package com.ohh.juc.utils.reentrantLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    private static final List<Long> data = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(ReadWriteLockExample::write).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(ReadWriteLockExample::read).start();
        /*new Thread(ReadWriteLockExample::read).start();
        new Thread(ReadWriteLockExample::read).start();*/
        /*new Thread(ReadWriteLockExample::write).start();
        new Thread(ReadWriteLockExample::write).start();*/
    }

    private static void write() {
        try {
            writeLock.lock();
            System.out.println("writing...");
            TimeUnit.SECONDS.sleep(5);
            data.add(System.currentTimeMillis());
            System.out.println("write over.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    private static void read() {
        try {
            readLock.lock();
            System.out.println("reading...");
            TimeUnit.SECONDS.sleep(5);
            data.forEach(System.out::println);
            System.out.println("read over.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }
}
