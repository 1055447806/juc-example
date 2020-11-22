package com.ohh.juc.collections.blocking;

import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample {

    public <T> PriorityBlockingQueue<T> create(int size) {
        return new PriorityBlockingQueue<>(size);
    }

    /**
     * PrioriBlockingQueue 的 add offer 和 put 方法是等价的
     * 它们使用的都是 offer 方法，当长度超过容量时，队列会扩容，
     * 且不能添加 null。
     */
    @Test
    public void testAddOfferPut() {
        PriorityBlockingQueue<String> queue = create(5);
        System.out.println("queue.add(\"Hello1\") = " + queue.add("Hello1"));
        System.out.println("queue.add(\"Hello1\") = " + queue.add("Hello2"));
        System.out.println("queue.add(\"Hello1\") = " + queue.add("Hello3"));
        System.out.println("queue.add(\"Hello1\") = " + queue.add("Hello4"));
        System.out.println("queue.add(\"Hello1\") = " + queue.add("Hello5"));
        System.out.println("queue.add(\"Hello1\") = " + queue.add("Hello6"));
    }

    /**
     * poll 直接拿，没有返回 null，
     * take 没有会阻塞，可被中断，
     * peek 没有返回 null，
     * element 展示首元素，不删除元素，为 null 抛异常
     * remove 展示首元素，不删除元素，为 null 不抛异常
     * poll 展示首元素，删除元素，为 null 不抛异常
     * take 展示首元素，删除元素，为 null 时阻塞
     */
    @Test
    public void testElement() throws InterruptedException {
        PriorityBlockingQueue<String> queue = create(5);
        queue.add("Hello1");
        queue.add("Hello2");
        queue.add("Hello3");
        System.out.println("queue.element() = " + queue.element());
        System.out.println("queue.element() = " + queue.element());

        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.peek() = " + queue.peek());

        System.out.println("queue.poll() = " + queue.poll());
        System.out.println("queue.poll() = " + queue.poll());

        System.out.println("queue.take() = " + queue.take());
        System.out.println("queue.take() = " + queue.take());
    }

    /**
     * PriorityBlockingQueue 无法添加未实现 Comparable 的对象
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    public void testAddNonComparable1() {
        PriorityBlockingQueue<Object> queue = new PriorityBlockingQueue<Object>();
        // 会报错
        queue.add(new ObjectNonComparable());
    }

    /**
     * PriorityBlockingQueue 如果添加未实现 Comparable 的对象，
     * 需要传递
     */
    @Test
    public void testAddNonComparable2() {
        PriorityBlockingQueue<Object> queue = new PriorityBlockingQueue<Object>(5, Comparator.comparingInt(Object::hashCode));
        // 不会报错
        boolean add = queue.add(new ObjectNonComparable());
        System.out.println(add);
    }

    /**
     * 没有实现 Comparable 接口的自定义类
     */
    @SuppressWarnings("InnerClassMayBeStatic")
    private class ObjectNonComparable {}
}
