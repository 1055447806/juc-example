package com.wgy.concurrent1.chapter10;

import java.util.Collection;

public interface Lock {
    class getLockTimeOutException extends Exception {
        public getLockTimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, getLockTimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();
}
