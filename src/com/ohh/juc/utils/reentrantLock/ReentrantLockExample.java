package com.ohh.juc.utils.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    private static final ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        /*IntStream.range(0, 2).forEach(i->{
            new Thread(ReentrantLockExample::needLock, "T" + i).start();
        });*/

        /*new Thread(ReentrantLockExample::testUnInterruptibly).start();
        TimeUnit.SECONDS.sleep(1);
        Thread secondThread = new Thread(ReentrantLockExample::testUnInterruptibly);
        secondThread.start();
        TimeUnit.SECONDS.sleep(1);
        while (true) {
            secondThread.interrupt();
            System.out.println(secondThread.getState());
            TimeUnit.SECONDS.sleep(1);
        }*/

        /*new Thread(ReentrantLockExample::testTryLock).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(ReentrantLockExample::testTryLock).start();*/

        System.out.println("lock.isLocked() = " + lock.isLocked());
        new Thread(ReentrantLockExample::testUnInterruptibly).start();
        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(ReentrantLockExample::testUnInterruptibly);
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("lock.getQueueLength() = " + lock.getQueueLength());
        System.out.println("lock.hasQueuedThreads() = " + lock.hasQueuedThreads());
        System.out.println("t2 is waiting? : " + lock.hasQueuedThread(t2));
        System.out.println("lock.isLocked() = " + lock.isLocked());
    }

    public static void testUnInterruptibly() {
        try {
//            lock.lock();
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " get the lock.");
            while (true) {
                //empty
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void testTryLock() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + " get the lock.");
                while (true) {

                }
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " get the lock failed.");
        }
    }

    public static void needLock() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " get the lock.");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void needLockBySync() {
        synchronized (ReentrantLockExample.class) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
