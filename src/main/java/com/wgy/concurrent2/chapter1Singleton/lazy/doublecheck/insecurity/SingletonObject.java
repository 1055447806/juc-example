package com.wgy.concurrent2.chapter1Singleton.lazy.doublecheck.insecurity;

/**
 * Singleton 设计模式 - 懒汉式，通过延迟实例的加载来实现 Singleton 设计模式
 * <p>
 * 通过 double-check 的方式进行简单的实现，线程不安全。
 *
 * @author Gary
 */
public class SingletonObject {

    /**
     * 类内部的实例引用
     */
    private static SingletonObject instance;

    /**
     * 获取实例引用的方法
     * <p>
     * 对 getInstance 方法直接加锁会严重的影响多线程情况下读取实例的性能，
     * 所以需要对其进行改进，使多线程读取的操作并行化，因此缩小锁的作用域，
     * 当执行到代码 ① 处时，若判断到实例引用为 null ，则对引用进行初始化，
     * 并对初始化的代码加锁，防止生成多个实例，若在代码 ① 处判断实例引用不为 null，
     * 则直接返回。
     * <p>
     * 在进行实例化操作的过程中，也就是加锁的代码内，仍然需要再对实例引用的值进行
     * 一次判断，因为可能会有多个线程同时进行初始化操作，所以代码 ② 处的判断
     * 确保了多线程情况下，实例只会被初始化一次，保证了线程的安全。
     * <p>
     * 但是这种方式仍然会存在问题，因为即便 synchronized 代码块中的内容被串行化，
     * 但是执行到代码 ① 处的线程仍然可以和已经执行到 synchronized 代码块内的线程进行并发，
     * 假设某个线程正在执行代码 ③，由于代码 ③ 处的操作不是原子性的操作，所以 JVM 可能对其
     * 进行优化，将初始化对象的一系列指令进行重排序，这样可能造成先把对象实例的内存地址
     * 赋值给 instance，再对实例进行其他初始化操作的结果，对于执行 synchronized 代码块的
     * 线程来说，这种情况不会产生程序的错误，因为即使指令被重排，其余被阻塞的线程也必须
     * 等待当前线程完成全部初始化操作，并解锁后才能获得执行权，但是如果是执行到代码 ① 处的
     * 线程，由于其未被加锁，所以会认为单例对象已经初始化完成，进而将其直接返回，可是由于
     * 指令重排的原因，对象内部的资源还没有准备好，所以，如果通过这个没有彻底完成初始化的
     * 实例去调用实例内部的资源，则可能会发生空指针异常的现象。
     *
     * @return 实例引用
     */
    public static SingletonObject getInstance() {
        if (null == instance) { // ①
            synchronized (SingletonObject.class) {
                if (null == instance) { // ②
                    instance = new SingletonObject(); // ③
                }
            }
        }
        return instance;
    }
}
