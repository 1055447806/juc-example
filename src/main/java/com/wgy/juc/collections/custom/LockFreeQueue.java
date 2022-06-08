package com.wgy.juc.collections.custom;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * 实现一个自定义的无锁队列
 * 保证了读和写的线程安全
 *
 * @param <E> 数据类型
 */
public class LockFreeQueue<E> {

    private AtomicReference<Node<E>> first, last;

    private AtomicInteger size = new AtomicInteger(0);

    public LockFreeQueue() {
        Node<E> node = new Node<>(null);
        first = new AtomicReference<>(node);
        last = new AtomicReference<>(node);
    }

    /**
     * 向队尾追加元素，对新旧节点的更新过程加锁，对新旧节点建立链接的过程不加锁。
     */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(Objects.requireNonNull(e, "Null value"));
        Node<E> oldLast = this.last.getAndSet(newNode);
        oldLast.next = newNode;
        this.size.incrementAndGet();
    }

    public E removeFirst() {
        Node<E> firstNode, nextNode;
        do {
            firstNode = this.first.get();
            nextNode = firstNode.next;
        } while (nextNode != null && !this.first.compareAndSet(firstNode, nextNode));
        E val = nextNode == null ? null : nextNode.element;
        if (nextNode != null) {
            nextNode.element = null;
        }
        size.decrementAndGet();
        return val;
    }

    public synchronized boolean isEmpty() {
        return this.first.get().next == null;
    }

    public static void main(String[] args) throws InterruptedException {
        final ConcurrentHashMap<Long, Object> data = new ConcurrentHashMap<>();
        final LockFreeQueue<Long> queue = new LockFreeQueue<>();
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 5).mapToObj(i -> (Runnable) () -> {
            while (counter.getAndDecrement() < 10) {
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.addLast(System.currentTimeMillis());
            }
        }).forEach(executorService::submit);

        TimeUnit.SECONDS.sleep(1);

        IntStream.range(0, 5).mapToObj(i -> (Runnable) () -> {
            while (!queue.isEmpty()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Long value = queue.removeFirst();
                System.out.println(value);
                data.put(value, new Object());
            }
        }).forEach(executorService::submit);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }

    /**
     * 自定义节点类
     *
     * @param <E> 数据类型
     */
    private static class Node<E> {
        private E element;
        private volatile Node<E> next;

        public Node(E element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return this.element == null ? "" : this.element.toString();
        }
    }


}
