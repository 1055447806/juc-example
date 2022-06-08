package com.wgy.concurrent2.chapter1Singleton.lazy.enumeration;

/**
 * Singleton 设计模式 - 懒汉式，通过延迟实例的加载来实现 Singleton 设计模式
 * <p>
 * 使用 Enumeration 进行实现，线程安全。
 * <p>
 * 通过枚举的方式对单例对象进行维护，枚举的构造方法是私有的且只会被加载一次，
 * 并且线程安全，所以可以通过在枚举中定义需要被维护的单例对象，
 * 通过 “Singleton.INSTANCE.getInstance()” 的方式获取单例对象。
 *
 * @author Gary
 */
public enum Singleton {

    INSTANCE;

    /**
     * 实例引用
     */
    private final SingletonObject instance;

    /**
     * 在构造函数中对 instance 进行初始化。
     */
    Singleton() {
        instance = new SingletonObject();
    }

    /**
     * 获取实例引用
     *
     * @return 实例引用
     */
    public SingletonObject getInstance() {
        return instance;
    }
}

/**
 * 单例对象类
 */
class SingletonObject {

}