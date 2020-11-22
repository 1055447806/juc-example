package com.ohh.juc.collections.blocking;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueExample {

    public <T> LinkedBlockingQueue<T> create() {
        return new LinkedBlockingQueue<>();
    }

    public <T> LinkedBlockingQueue<T> create(int capacity) {
        return new LinkedBlockingQueue<>(capacity);
    }

    /**
     * 当定义容量的时候，queue 满的时候使用 add 方法会抛出异常，
     */
    @Test
    public void testAddWithCapacity() {
        LinkedBlockingQueue<String> queue = create(2);
        queue.add("Hello1");
        queue.add("Hello2");
        queue.add("Hello3");
    }

    /**
     * 当定义容量的时候，queue 满的时候使用 offer 方法会返回 false，
     */
    @Test
    public void testOfferWithCapacity() {
        LinkedBlockingQueue<String> queue = create(2);
        System.out.println("queue.offer(\"Hello1\") = " + queue.offer("Hello1"));
        System.out.println("queue.offer(\"Hello2\") = " + queue.offer("Hello2"));
        System.out.println("queue.offer(\"Hello3\") = " + queue.offer("Hello3"));
    }

    /**
     * 当定义容量的时候，queue 满的时候使用 put 方法会阻塞，
     */
    @Test
    public void testPutWithCapacity() throws InterruptedException {
        LinkedBlockingQueue<String> queue = create(2);
        queue.put("Hello1");
        queue.put("Hello2");
        queue.put("Hello3");
    }

    /**
     * 当未定义容量的时候，随便加
     */
    @Test
    public void testAddWithNoCapacity() throws InterruptedException {
        LinkedBlockingQueue<String> queue = create();
        queue.add("Hello1");
        queue.add("Hello2");
        queue.add("Hello3");
        System.out.println("queue.offer(\"Hello1\") = " + queue.offer("Hello1"));
        System.out.println("queue.offer(\"Hello2\") = " + queue.offer("Hello2"));
        System.out.println("queue.offer(\"Hello3\") = " + queue.offer("Hello3"));
        queue.put("Hello1");
        queue.put("Hello2");
        queue.put("Hello3");
        System.out.println("queue.size() = " + queue.size());
    }

    /**
     * element 方法展示首元素，没有元素时抛出异常
     */
    @Test
    public void testElement() {
        LinkedBlockingQueue<String> queue = create();
        queue.add("Hello1");
        queue.add("Hello2");
        System.out.println("queue.element() = " + queue.element());
        System.out.println("queue.element() = " + queue.element());
        queue.clear();
        System.out.println("queue.element() = " + queue.element());
    }

    /**
     * element 方法展示首元素，没有元素时返回 null
     */
    @Test
    public void testPeek() {
        LinkedBlockingQueue<String> queue = create();
        queue.add("Hello1");
        queue.add("Hello2");
        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.peek() = " + queue.peek());
        queue.clear();
        System.out.println("queue.peek() = " + queue.peek());
    }

    /**
     * poll 方法弹出首元素，没有元素时返回 null
     */
    @Test
    public void testPoll() {
        LinkedBlockingQueue<String> queue = create();
        queue.add("Hello1");
        queue.add("Hello2");
        System.out.println("queue.poll() = " + queue.poll());
        System.out.println("queue.poll() = " + queue.poll());
        queue.clear();
        System.out.println("queue.poll() = " + queue.poll());
    }

    /**
     * remove 方法弹出首元素，没有元素时抛出异常
     */
    @Test
    public void testRemove() {
        LinkedBlockingQueue<String> queue = create();
        queue.add("Hello1");
        queue.add("Hello2");
        System.out.println("queue.remove() = " + queue.remove());
        System.out.println("queue.remove() = " + queue.remove());
        queue.clear();
        System.out.println("queue.remove() = " + queue.remove());
    }
}
