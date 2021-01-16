package com.ohh.concurrent2.chapter3volatile;

/**
 * volatile 关键字 - 有序性
 * <p>
 * 有序性保证了指令的执行顺序不会被 JVM 优化，比如在本实验中，
 * 启动两个线程，其中一个线程对 obj 进行初始化操作，另一个线程
 * 监测 obj 的状态，当 obj 初始化完成后对其进行消费。
 * <p>
 * 其中代码 ① 处和代码 ② 处有可能被 JVM 优化，导致先执行代码 ②，
 * 再执行代码 ①，这就会造成监测线程发生空指针异常，因此，此时可以
 * 使用 volatile 对 init 变量进行修饰，保证代码 ② 处之前的代码
 * 会在其之前执行，代码 ② 处之后的代码会在其之后执行，这样就保证了
 * 指令的执行顺序，从而保证了有序性，避免因 JVM 对指令执行顺序的优化
 * 导致的错误的发生。
 *
 * @author Gary
 */
public class VolatileTest3 {

    private static volatile boolean init = false;

    private static Object obj;

    public static void main(String[] args) {

        // 启动一个线程，对 obj 进行初始化
        new Thread(() -> {
            obj = new Object(); // ①
            init = true; // ②
        }).start();

        // 监测 obj 的状态，当 obj 被初始化之后进行消费
        new Thread(() -> {
            while (!init) {
            }
            int hashcode = obj.hashCode();
        });
    }
}
