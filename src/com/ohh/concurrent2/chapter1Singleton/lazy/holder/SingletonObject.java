package com.ohh.concurrent2.chapter1Singleton.lazy.holder;

/**
 * Singleton 设计模式 - 懒汉式，通过延迟实例的加载来实现 Singleton 设计模式
 * <p>
 * 通过 Holder 的方式进行实现，即维护一个私有静态内部类的方式进行实现，线程安全。
 *
 * @author Gary
 */
public class SingletonObject {

    /**
     * 类内部的实例 Holder
     * <p>
     * 不论是 “饿汉式”，还是通过各种方式实现的 “懒汉式”，其内部本质上都存在一个实例的引用，
     * 通过维护并返回这个引用，实现 Singleton 模式，引用的维护遵循了几个规则，需要保证实例引用的线程安全，
     * 使其只能被初始化一次，同时还应该使用延迟加载的方式进行实现，保证程序的性能，而这些特点，
     * 都和 Java 语言中的静态内部类的类加载机制相同，所以，与其手动的维护一个实例的引用，
     * 不如通过编写一个静态内部类，并用静态内部类来维护这个引用，这样的实现简单又高效。
     */
    private static class InstanceHolder {
        private static final SingletonObject instance = new SingletonObject();
    }


    /**
     * 获取实例的引用
     *
     * @return 实例的引用
     */
    public static SingletonObject getInstance() {
        return InstanceHolder.instance;
    }
}
