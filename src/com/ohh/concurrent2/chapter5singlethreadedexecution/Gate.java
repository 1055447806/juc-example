package com.ohh.concurrent2.chapter5singlethreadedexecution;

/**
 * 模拟一个门，这个门可以被通过
 *
 * @author Gary
 */
public class Gate {

    private int counter;
    private String name;
    private String address;

    /**
     * 模拟通过门的方法
     * <p>
     * 为 name 和 address 赋值，并对其进行验证，因为多个线程共享了这些变量，
     * 并对其进行了写操作，因此如果不加锁，则会引发各种线程安全问题。
     *
     * @param name    被共享的资源
     * @param address 被共享的资源
     */
    public synchronized void pass(String name, String address) {
        this.counter++;
        this.name = name;
        this.address = address;
        verify();
    }

    /**
     * 验证 name 和 address 的首字母是否相同，
     * 如果不同则输出 name 和 address 的值。
     */
    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("BROKEN：" + this.toString());
        }
    }

    @Override
    public String toString() {
        return "Gate{" +
                "counter=" + counter +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
