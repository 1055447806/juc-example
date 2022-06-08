package com.wgy.concurrent2.chapter1Singleton.lazy.ordinary.security;

/**
 * Singleton 设计模式 - 懒汉式，通过延迟实例的加载来实现 Singleton 设计模式
 * <p>
 * 延迟加载的简单实现，对 getInstance 方法加锁，线程安全。
 *
 * @author Gary
 */
public class SingletonObject {

    /**
     * 类内部的实例引用
     */
    private static SingletonObject instance;

    /**
     * 获取实例的方法
     *
     * 通过对获取实例前，对实例引用的判断加锁的方式，解决了多线程情况下的线程安全问题，
     * 但是，也因此产生了效率低下的问题，因为 Singleton 模式中，只需要对实例进行一次写操作，
     * 也就是第一次访问实例时，对实例的初始化操作，而后续的操作都是获取实例的引用，是对实例的读操作，
     * 如果对 getInstance 方法加锁，则将读操作变为串行化的操作，极大地降低了程序的性能。
     *
     * @return 实例的引用
     */
    public synchronized static SingletonObject getInstance() {
        if (null == instance)
            return new SingletonObject();
        return instance;
    }
}
