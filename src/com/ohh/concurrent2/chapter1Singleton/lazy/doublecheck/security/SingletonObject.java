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
     * 类内部的实例引用，使用 volatile 关键字进行了修饰，禁止了 JVM 对指令的优化，
     * 有效的解决了代码 ① 处和 代码 ③ 处的并发由于指令重排所带来的隐患。
     */
    private static volatile SingletonObject instance;

    /**
     * 获取实例的引用
     *
     * @return 实例的引用
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
