package com.ohh.juc.collections.blocking;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Double-End-Queue
 */
public class LinkedBlockingDeQueExample {

    public <T> LinkedBlockingDeque<T> create() {
        return new LinkedBlockingDeque<>();
    }

    /**
     * removeFirst 移除头元素，如果链表为空会抛出异常。
     */
    @Test
    public void testAddFirstRemoveFirst() {
        LinkedBlockingDeque<String> deque = create();
        deque.addFirst("Java");
        deque.addFirst("Scala");
        System.out.println("deque.removeFirst() = " + deque.removeFirst());
        System.out.println("deque.removeFirst() = " + deque.removeFirst());
        System.out.println("deque.removeFirst() = " + deque.removeFirst());
    }
    /**
     * remove 等价于 removeFirst
     */
    @Test
    public void testAddRemove() {
        LinkedBlockingDeque<String> deque = create();
        deque.addFirst("Java");
        deque.addFirst("Scala");
        System.out.println("deque.remove() = " + deque.remove());
        System.out.println("deque.remove() = " + deque.remove());
        System.out.println("deque.remove() = " + deque.remove());
    }

    /**
     * takeFirst 方法会阻塞
     * take 方法等价于 takeFirst
     */
    @Test
    public void testTakeFirst() throws InterruptedException {
        LinkedBlockingDeque<String> deque = create();
        Executors.newSingleThreadScheduledExecutor().schedule(() -> deque.add("Java"),5,TimeUnit.SECONDS);
        long start = System.currentTimeMillis();
        deque.takeFirst();
        System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
    }
}
