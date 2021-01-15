package com.ohh.concurrent2.chapter1Singleton.lazy.doublecheck.security;

/**
 * Singleton 设计模式 - 懒汉式，通过延迟实例的加载来实现 Singleton 设计模式
 * <p>
 * 通过 double-check 的方式进行实现，并使用 volatile 对实例引用进行修饰，线程安全。
 *
 * @author Gary
 */
public class SingletonObject {

    /**
     * 类内部的实例引用，使用 volatile 关键字进行了修饰，在线程获取该引用的值时，
     * 若引用的值发生了变更，会重新从主存中获取这个新值，而不是中 cache 中直接读取，
     * 从而保证了可见性，解决了 double-check 机制带来的由于更新不及时，重复初始化多个实例的问题。
     */
    private static volatile SingletonObject instance;

    /**
     * 获取实例的引用
     *
     * @return 实例的引用
     */
    public static SingletonObject getInstance() {
        if (null == instance) {
            synchronized (SingletonObject.class) {
                if (null == instance) {
                    instance = new SingletonObject();
                }
            }
        }
        return instance;
    }
}
