package com.ohh.concurrent2.chapter5singlethreadedexecution;

/**
 * Single Threaded Execution 模式
 * <p>
 * 通过这个简单的例子，模拟多个线程对同一实例进行操作时的线程安全问题。
 * <p>
 * 其中 gate 是被多个线程共享的对象，每个线程在执行的时候都访问了 gate 的内部资源，
 * 并对 gate 的内部资源进行了写操作，因此便引发了线程安全问题，在控制台中打印了大量的语句，
 * 这些语句是不应该被打印的，所以应该对 Gate 类中的 pass 方法加锁，
 * 使其对共享对象内部的共享资源的读写操作串行化，以此来避免线程安全问题的产生。
 *
 * @author Gary
 */
public class Main {
    public static void main(String[] args) {
        // 共享实例
        Gate gate = new Gate();

        // 定义三个线程
        UserThread bj = new UserThread("Baobao", "BeiJing", gate);
        UserThread sh = new UserThread("ShangLao", "ShangHai", gate);
        UserThread gz = new UserThread("GuangLao", "GuangZhou", gate);

        // 启动线程
        bj.start();
        sh.start();
        gz.start();
    }
}
