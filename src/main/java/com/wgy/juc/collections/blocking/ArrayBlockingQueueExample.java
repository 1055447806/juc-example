package com.wgy.juc.collections.blocking;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueExample {

    public static <T> ArrayBlockingQueue<T> create(int size) {
        return new ArrayBlockingQueue<>(size);
    }


    /**
     * add 方法加满会抛异常，成功返回 true
     */
    @Test
    public void testAdd() {
        ArrayBlockingQueue<String> queue = create(5);
        queue.add("Hello1");
        queue.add("Hello2");
        queue.add("Hello3");
        queue.add("Hello4");
        queue.add("Hello5");
        queue.add("Hello6");
    }

    /**
     * offer 方法加满会返回 false，成功返回 true
     */
    @Test
    public void testOffer() {
        ArrayBlockingQueue<String> queue = create(5);
        System.out.println("queue.offer(\"Hello1\") = " + queue.offer("Hello1"));
        System.out.println("queue.offer(\"Hello2\") = " + queue.offer("Hello2"));
        System.out.println("queue.offer(\"Hello3\") = " + queue.offer("Hello3"));
        System.out.println("queue.offer(\"Hello4\") = " + queue.offer("Hello4"));
        System.out.println("queue.offer(\"Hello5\") = " + queue.offer("Hello5"));
        System.out.println("queue.offer(\"Hello6\") = " + queue.offer("Hello6"));
    }

    /**
     * put 方法加满会阻塞
     */
    @Test
    public void testPut() throws InterruptedException {
        ArrayBlockingQueue<String> queue = create(5);
        queue.put("Hello1");
        queue.put("Hello2");
        queue.put("Hello3");
        queue.put("Hello4");
        queue.put("Hello5");
        queue.put("Hello6");
    }

    /**
     * poll 方法会弹出头元素
     * 如果没有元素返回 null
     */
    @Test
    public void testPoll() throws InterruptedException {
        ArrayBlockingQueue<String> queue = create(2);
        queue.put("Hello1");
        queue.put("Hello2");
        System.out.println("queue.poll() = " + queue.poll());
        System.out.println("queue.poll() = " + queue.poll());
        System.out.println("queue.poll() = " + queue.poll());
    }

    /**
     * remove 方法会弹出头元素
     * 如果没有元素会抛出异常
     */
    @Test
    public void testRemove() throws InterruptedException {
        ArrayBlockingQueue<String> queue = create(2);
        queue.put("Hello1");
        queue.put("Hello2");
        System.out.println("queue.remove() = " + queue.remove());
        System.out.println("queue.remove() = " + queue.remove());
        System.out.println("queue.remove() = " + queue.remove());
    }

    /**
     * take 方法弹出头元素，
     * 如果为空则阻塞。
     */
    @Test
    public void testTake() throws InterruptedException {
        ArrayBlockingQueue<String> queue = create(2);
        queue.put("Hello1");
        queue.put("Hello2");
        System.out.println("queue.take() = " + queue.take());
        System.out.println("queue.take() = " + queue.take());
        System.out.println("queue.take() = " + queue.take());
    }

    /**
     * peek 方法会展示头元素，
     * 如果没有元素返回 null
     */
    @Test
    public void testPeek() throws InterruptedException {
        ArrayBlockingQueue<String> queue = create(2);
        queue.put("Hello1");
        queue.put("Hello2");
        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.peek() = " + queue.peek());

        queue.clear();
        System.out.println("queue.peek() = " + queue.peek());
    }

    /**
     * element 方法会展示头元素，
     * 如果没有元素抛出异常
     */
    @Test
    public void testElement() throws InterruptedException {
        ArrayBlockingQueue<String> queue = create(2);
        queue.put("Hello1");
        queue.put("Hello2");
        System.out.println("queue.element() = " + queue.element());
        System.out.println("queue.element() = " + queue.element());
        System.out.println("queue.element() = " + queue.element());
        System.out.println("queue.element() = " + queue.element());
        System.out.println("queue.element() = " + queue.element());

        queue.clear();
        System.out.println("queue.element() = " + queue.element());
    }

    /**
     * drainTo 方法将 queue 中元素排干到一个集合中
     */
    @Test
    public void testDrainTo() {
        ArrayBlockingQueue<String> queue = create(5);
        queue.add("Hello1");
        queue.add("Hello2");
        queue.add("Hello3");
        queue.add("Hello4");
        queue.add("Hello5");
        List<String> list = new ArrayList<>();
        queue.drainTo(list);
        System.out.println("list.size() = " + list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void test() {
        ArrayBlockingQueue<String> queue = create(5);
        queue.add("Hello1");
        queue.add("Hello2");
        queue.add("Hello3");
        queue.add("Hello4");
        queue.add("Hello5");
        System.out.println("queue.remainingCapacity() = " + queue.remainingCapacity());
        queue.remove();
        queue.remove();
        queue.remove();
        queue.remove();
        queue.remove();
        System.out.println("queue.remainingCapacity() = " + queue.remainingCapacity());
    }
}
