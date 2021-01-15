package com.ohh.concurrent2.chapter3volatile;

import java.util.concurrent.TimeUnit;

/**
 * volatile 关键字 - 可见性
 * <p>
 * 可见性是多线程环境中需要被保证的特性之一，中什么是可见性，比如多个线程
 * 共享了同一个量 value，如果一个线程修改了 value 那么其他线程是否会及时的
 * 更新 value 的值呢？通过这个案例，可以观察到，如果不使用 volatile
 * 关键字进行修饰，那么线程获取变量的值时，某些情况下，永远都是
 * 从 cpu 的 cache中进行读取，而不会主动地去获取新值。
 * <p>
 * 这个实验的思路很简单：
 * 有一个被 2 个线程共享的变量 value 的初始值是 0，
 * 还有一个常量 MAX_VALUE，其中一个线程不停地读取 value的值，
 * 直到 value 的值到达了 MAX_VALUE，则输出 “value 的值到达了上限”，
 * 而另一个线程则不停地对 value 进行自增操作。
 * <p>
 * 通过实验观察可以得出结论：只有当 value 变量使用 volatile 关键字
 * 进行修饰后，线程在获取 value 的值的时候才会及时的获取被更新后的值，
 * 否则可能将永远使用线程所在的 cpu 的 cache 中已经缓存的值。
 *
 * @author Gary
 */
public class VolatileTest {

    /**
     * 共享变量
     */
    private static volatile int value = 0;

    /**
     * 上限值
     */
    private static final int MAX_VALUE = 5;

    public static void main(String[] args) throws InterruptedException {

        // 启动一个线程，并不停地循环判断 value 的值，直到 value 的值达到 MAX_VALUE
        new Thread(() -> {
            while (value < MAX_VALUE) {
            }
            System.out.println("value 的值到达了上限");
        }).start();

        // 等待线程启动成功
        TimeUnit.SECONDS.sleep(1);

        // 启动第二个线程对 value 不断地进行自增操作
        new Thread(() -> {
            while (value < MAX_VALUE) {
                ++value;
            }
        }).start();
    }
}
