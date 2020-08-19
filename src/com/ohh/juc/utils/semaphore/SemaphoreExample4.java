package com.ohh.juc.utils.semaphore;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample4 {
    public static void main(String[] args) throws InterruptedException {

        final MySemaphore semaphore = new MySemaphore(5);
        new Thread(() -> {
            int count = 0;
            try {
                count = semaphore.drainPermits();
                System.out.println("T1 get permits count = " + count);
                System.out.println("semaphore.availablePermits() = " + semaphore.availablePermits());
                TimeUnit.SECONDS.sleep(5);
                System.out.println("There are " + semaphore.getQueueLength() + " threads waiting for permit");
                System.out.println("semaphore.hasQueuedThreads() = " + semaphore.hasQueuedThreads());
                semaphore.getQueuedThreads().forEach(t -> System.out.println(t.getName() + " is waiting..."));
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("T1 release...");
                semaphore.release(count);
            }
        }, "T1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            try {
                System.out.println("T2 try get permit: "+semaphore.tryAcquire(1, TimeUnit.SECONDS));
                semaphore.acquire();
                System.out.println("T2 get permit");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }, "T2").start();


    }

    static class MySemaphore extends Semaphore {
        public MySemaphore(int permits) {
            super(permits);
        }

        public MySemaphore(int permits, boolean fair) {
            super(permits, fair);
        }

        @Override
        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }
}
