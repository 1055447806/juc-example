package com.ohh.concurrent2.chapter1Singleton.lazy.doublecheck.insecurity;

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
     *
     * 在进行实例化操作的过程中，也就是加锁的代码内，仍然需要再对实例引用的值进行
     * 一次判断，因为可能会有多个线程同时进行初始化操作，所以代码 ② 处的判断
     * 确保了多线程情况下，实例只会被初始化一次，保证了线程的安全。
     *
     * 但是这种方式仍然会存在问题，即可见性问题，什么是可见性问题，即一个线程更新了值，
     * 但另一个线程没有重新获取这个值，导致仍然使用旧值，也就是 null，所以又重新对实例
     * 进行了初始化。
     *
     * 为什么会有可见性问题，因为使用了 double-check 机制，因为在这个机制中，
     * 对实例引用 instance 进行了 2 次判断，即代码 ① 和 代码 ②，所以在代码的执行过程中，
     * 可能因为对指令的优化，cpu 因为已经获取了一次 instance 的值，并将值缓存到了 cpu 的 cache 中，
     * 每个线程的 cache 是独立的，所以在第二次获取值的时候，直接从 cache 中进行读取，
     * 即便这个值已经被其他的线程进行了修改，也不会去重新获取，因此，便产生了可见性的问题。
     *
     * @return 实例引用
     */
    public static SingletonObject getInstance() {
        if (null == instance) { // ①
            synchronized (SingletonObject.class) {
                if (null == instance) { // ②
                    instance = new SingletonObject();
                }
            }
        }
        return instance;
    }
}
