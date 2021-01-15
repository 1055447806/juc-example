package com.ohh.concurrent2.chapter1Singleton.simple;

/**
 * Singleton 设计模式 - 饿汉式，最简单的 Singleton 设计模式的实现
 * <p>
 * 在类内部维护一个 “使用 private static final 修饰的不可变的静态实例”，
 * 该实例会在类加载时进行初始化，外部无法对实例的引用进行修改，任何时刻都返回同一实例，
 * 通过这种方式，就实现了单例设计模式。
 * <p>
 * 这种方式的特点是：实现简单，线程安全，但是比较浪费系统资源，因为类会主动加载实例，
 * 如果不需要使用这个实例，那么该实例就会一直占用系统资源。
 *
 * @author Gary
 */
public class SingletonObject {

    /**
     * 类内部维护的 “不可变静态” 实例
     */
    private static final SingletonObject instance = new SingletonObject();

    /**
     * 获取实例的方法
     *
     * @return 实例
     */
    public static SingletonObject getInstance() {
        return instance;
    }
}
