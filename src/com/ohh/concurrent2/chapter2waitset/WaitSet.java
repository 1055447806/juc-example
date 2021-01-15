package com.ohh.concurrent2.chapter2waitset;

import java.util.stream.IntStream;

/**
 * 多线程使用 wait 与 notify 后执行顺序实验，验证 wait set 是如何实现
 * <p>
 * 通过使用 synchronized 关键字，可以使被包裹的代码块的执行串行化，
 * 那么这是什么原理呢？通过这个实验可以观察并得出结论。
 * <p>
 * 当通过 monitor 对象调用 wait 方法时，线程会进入 block 状态，
 * 并进入一个等待队列 wait set 中，虽然网上的说法众说纷纭，
 * 但是在本实验中，这个 wait set 是一个 FIFO 队列，也就是说，
 * 线程按照一定的顺序加入等待的队列中后，通过 monitor 对象调用 notify 方法，
 * 最先被加入 wait set 队列的线程会被唤醒，而唤醒后的线程又会加入一个 LIFO 栈中，
 * 在争抢锁时，最后入栈的线程会最先被执行。
 * <p>
 * 比如在本实验中，首先按顺序启动 10 个线程，并通过调用 wait 方法使它们进入 block 状态，
 * 线程的名字是它们启动顺序的序号，依次为 1  2  3  4  5  6  7  8  9  10，
 * 等待线程全部进入阻塞状态后，开始执行 10 次 notify 方法，通过实验可以观察到，
 * 如果每次执行 notify 后都都休眠一定的时间，等待线程被唤醒，则控制台的输出如下：
 * <pre>
 *     1  2  3  4  5  6  7  8  9  10
 *     1  2  3  4  5  6  7  8  9  10
 *     Process finished with exit code 0
 * </pre>
 * 上面一行表示线程的启动顺序，下面一行表示线程被唤醒的顺序，
 * 其中下面一行会按一定时间间隔逐个进行打印，因此可以推断线程的 wait set 是个 FIFO 队列。
 * <p>
 * 而如果去掉 notify 方法执行后的等待时间，一次性在极短的时间内执行 10 次 notify 方法，
 * 则控制台第二行的打印顺序会变为乱序，如下：
 * <pre>
 *     1  3  2  4  5  6  7  8  9  10
 *     1  3  5  4  2  10  9  8  7  6
 *     Process finished with exit code 0
 * </pre>
 * 我们可以将这段输出分段观察：
 * <pre>
 *     1      3      2  4  5      6  7  8  9  10
 *     1      3      5  4  2      10  9  8  7  6
 * </pre>
 * 其中有 4 个部分，每个部分打印的顺序都与上面的 FIFO 队列相反，因此，代码的执行逻辑是，
 * 1. 主线程调用了一次 notify 方法，wait set 中线程 1 出队并执行，
 * 2. 主线程调用了一次 notify 方法，wait set 中线程 3 出队并执行，
 * 3. 主线程连续调用了三次 notify 方法，wait set 中线程 2 4 5 出队并加入 LIFO 栈，
 * 并按逆序弹栈，依次执行并输出了 5 4 2。
 * 4. 主线程连续调用了五次 notify 方法，wait set 中线程 6  7  8  9  10 出队并加入 LIFO 栈，
 * 并按逆序弹栈，依次执行并输出了 10  9  8  7  6。
 * 因此，被唤醒的线程拿到锁的顺序的为 LIFO。
 *
 * @author Gary
 */
public class WaitSet {

    /**
     * monitor 对象
     */
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        // 启动多个线程并对其分别调用 wait 方法
        IntStream.rangeClosed(1, 10).forEach(WaitSet::startThreadAndWait);

        // 等待一定的时间使所有线程都进入阻塞状态
        Thread.sleep(3000);
        System.out.println();

        // 调用多次 notify 方法
        IntStream.rangeClosed(1, 10).forEach(WaitSet::notifyThread);
    }

    /**
     * 创建线程，启动线程，为线程命名并在启动后调用 wait 方法使其阻塞
     *
     * @param threadName 线程名
     */
    public static void startThreadAndWait(int threadName) {
        new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.print(Thread.currentThread().getName() + "  ");
                    LOCK.wait();
                    System.out.print(Thread.currentThread().getName() + "  ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, String.valueOf(threadName)).start();
    }

    /**
     * 调用一次 notify 方法
     *
     * @param i 序号
     */
    public static void notifyThread(int i) {
        synchronized (LOCK) {
            LOCK.notify();
        }
        /*
        // 每次执行 notify 后都等待 1 秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}
