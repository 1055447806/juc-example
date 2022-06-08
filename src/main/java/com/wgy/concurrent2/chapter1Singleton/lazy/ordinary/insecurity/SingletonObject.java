package com.wgy.concurrent2.chapter1Singleton.lazy.ordinary.insecurity;

/**
 * Singleton 设计模式 - 懒汉式，通过延迟实例的加载来实现 Singleton 设计模式
 * <p>
 * 延迟加载的简单实现，因为没有加锁，所以线程不安全。
 *
 * @author Gary
 */
public class SingletonObject {

    /**
     * 类内部维护的 “静态” 实例引用
     * <p>
     * 因为需要通过延迟加载的方式进行初始化，所以在类加载时不对其进行初始化，因此实例引用为非 final 类型。
     */
    private static SingletonObject instance;

    /**
     * 获取实例的方法
     * <p>
     * 用延迟加载的方式进行实例初始化，在调用方法时，判断实例的引用是否为 null，
     * 如果为 null，则初始化实例，并将其返回，如果不为 null，则直接返回实例。
     * <p>
     * 通过这种方式实现的延迟加载存在线程安全的问题，在第一个线程执行的过程中，
     * 如果线程执行到位置 ①，此时另一个线程获取了 cpu 的执行权，并执行了位置 ① 和 ② 的代码，
     * 那么实例会被第二个线程进行初始化，但是此时第一个线程仍然会执行代码 ②，
     * 所以，因为没有加锁，在多线程环境下实例会被初始化多次。
     *
     * @return 实例的引用
     */
    public static SingletonObject getInstance() {
        if (null == instance) // ①
            return new SingletonObject(); // ②
        return instance;
    }
}
