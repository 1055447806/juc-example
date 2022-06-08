package com.wgy.concurrent2.chapter6readwritelock;

/**
 * 读写锁类
 */
public class ReadWriteLock {

    //正在 进行读操作 的线程数
    private int readingReaders = 0;
    //正在 等待读操作 的线程数
    private int waitingReaders = 0;
    //正在 进行写操作 的线程数
    private int writingWriters = 0;
    //正在 等待写操作 的线程数
    private int waitingWriters = 0;
    //写入线程优先级是否为高
    private boolean preferWriter;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    /**
     * 读锁
     *
     * @throws InterruptedException
     */
    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders++;
        try {
            while (writingWriters > 0 || (preferWriter && waitingWriters > 0)) {
                this.wait();
            }
            readingReaders++;
        } finally {
            this.waitingReaders--;
        }
    }

    public synchronized void readUnlock() {
        this.readingReaders--;
        this.notifyAll();
    }

    /**
     * 写锁
     *
     * @throws InterruptedException
     */
    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters++;
        try {
            while (writingWriters > 0 || readingReaders > 0) {
                this.wait();
            }
            this.writingWriters++;
        } finally {
            this.waitingWriters--;
        }
    }

    public synchronized void writeUnlock() {
        this.writingWriters--;
        this.notifyAll();
    }
}
