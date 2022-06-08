package com.wgy.concurrent1.chapter10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BooleanLock implements Lock {

    /**
     * 当锁未被抢占时是false, 被抢占时则为true
     */
    private boolean initValue;

    /**
     * 正在占用锁资源的线程对象
     */
    private Thread currentThread;

    /**
     * 所有等待线程的集合
     */
    private Collection<Thread> waitingThreadCollection = new ArrayList<>();

    public BooleanLock() {
        this.initValue = false;
    }

    /**
     * 默认超时时长为0
     *
     * @throws InterruptedException
     */
    @Override
    public synchronized void lock() throws InterruptedException {
        try {
            lock(0);
        } catch (getLockTimeOutException e) {
            e.printStackTrace();
        }
    }

    /**
     * 可以为锁设置超时时间, 当一个线程在指定时间内仍没有获取到锁资源, 则抛出超时异常
     *
     * @param mills
     * @throws InterruptedException
     * @throws getLockTimeOutException
     */
    @Override
    public synchronized void lock(long mills) throws InterruptedException, getLockTimeOutException {
        if (initValue) {
            waitingThreadCollection.add(Thread.currentThread());
            if (mills <= 0) {
                do {
                    this.wait();
                } while (initValue);
            } else {
                long endTime = System.currentTimeMillis() + mills;
                do {
                    if (endTime > System.currentTimeMillis()) {
                        this.wait(endTime - System.currentTimeMillis());
                    } else {
                        waitingThreadCollection.remove(Thread.currentThread());
                        throw new getLockTimeOutException("Time out");
                    }
                } while (initValue);
            }
        }
        waitingThreadCollection.remove(Thread.currentThread());
        this.initValue = true;
        this.currentThread = Thread.currentThread();
        Optional.of(Thread.currentThread().getName() + " get the lock monitor").ifPresent(System.out::println);
    }

    @Override
    public synchronized void unlock() {
        if (this.currentThread == Thread.currentThread()) {
            this.initValue = false;
            Optional.of(Thread.currentThread().getName() + " release the lock monitor").ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(waitingThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return waitingThreadCollection.size();
    }
}
