package com.ohh.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CompareAndSetLock {

    private AtomicInteger value = new AtomicInteger(0);

    private Thread lockedThread;

    public void tryLock() throws GetLockException {
        if (value.compareAndSet(0, 1)) {
            lockedThread = Thread.currentThread();
        } else {
            throw new GetLockException("get the lock failed");
        }
    }

    public void unLock() {
       if (lockedThread == Thread.currentThread()) {
            value.compareAndSet(1, 0);
        }
    }
}
