package com.ohh.concurrent2.chapter3volatile;

/**
 * 不加 volatile 关键字修饰的变量是否永远会从 cpu 的 cache 中读取？
 * <p>
 * 答案是否定的，比如下面的程序，当线程中既有读操作又有写操作的时候，
 * 线程就会从主存中取值，下面的例子中，两个线程同时对 value 进行自增
 * 操作。
 * <p>
 * 假设每次都从 cpu 的 cache 中进行取值，那么先启动的线程则
 * 必然会进行 100000 次自增操作，而实际的结果是，没有任何线程执行
 * 自增的次数达到了 100000 次，这说明后启动的线程为先启动的线程分担了
 * 一部分任务，同时也说明了尽管没有使用 volatile 对 value 进行修饰，
 * 可此时线程仍然不会只从 cpu 的 cache 中进行取值。
 *
 * @author Gary
 */
public class VolatileTest2 {

    private static int value = 0;

    private static final int MAX_VALUE = 100000;

    public static void main(String[] args) {

        // 启动第一个线程
        new Thread(() -> {
            int count = 0;
            while (value < MAX_VALUE) {
                ++value;
                ++count;
            }
            System.out.println("T1 共执行的次数：" + count);
        }).start();

        // 启动第二个线程
        new Thread(() -> {
            int count = 0;
            while (value < MAX_VALUE) {
                ++value;
                ++count;
            }
            System.out.println("T2 共执行的次数：" + count);
        }).start();
    }
}
