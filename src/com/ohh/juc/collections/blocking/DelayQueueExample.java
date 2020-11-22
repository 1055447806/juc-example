package com.ohh.juc.collections.blocking;

import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {

    public static <T extends Delayed> DelayQueue<T> create() {
        return new DelayQueue<>();
    }

    /**
     * DelayQueue 不能添加 null，否则会抛出异常，
     * 并且只能添加实现了 Delayed 接口的元素。
     * add offer put 等价。
     */
    @Test
    public void testAddOfferPutNull() {
        DelayQueue<DelayElement<String>> delayQueue = create();
        delayQueue.add(null);
    }

    /**
     * element 和 peek 方法展示首元素，没有延迟，
     * element 如果元素为空抛出异常，peek 方法如果元素为空返回 null
     */
    @Test
    public void testElementAndPeek() {
        DelayQueue<DelayElement<String>> delayQueue = create();
        delayQueue.add(DelayElement.of("Delayed1", 1000));
        long start = System.currentTimeMillis();
        System.out.println("delayQueue.element() = " + delayQueue.element());
        System.out.println("delayQueue.peek() = " + delayQueue.peek());
        System.out.println("time = " + (System.currentTimeMillis() - start));
    }

    /**
     * 使用迭代器的方式，没有延迟，而且可以发现 delayQueue 按时间进行排序。
     */
    @Test
    public void testIterator() {
        DelayQueue<DelayElement<String>> delayQueue = create();
        delayQueue.add(DelayElement.of("Delayed1", 1000));
        delayQueue.add(DelayElement.of("Delayed2", 800));
        delayQueue.add(DelayElement.of("Delayed3", 10000));
        delayQueue.add(DelayElement.of("Delayed4", 20000));
        System.out.println("delayQueue.size() = " + delayQueue.size());
        long start = System.currentTimeMillis();
        Iterator<DelayElement<String>> it = delayQueue.iterator();
        while (it.hasNext()) {
            System.out.println("it.next() = " + it.next().getDate());
        }
        System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
    }

    /**
     * remove 方法如果首元素没有到达延时时间，那么会抛出异常。
     */
    @Test
    public void testRemove() {
        DelayQueue<DelayElement<String>> delayQueue = create();
        delayQueue.add(DelayElement.of("Delayed1", 1000));
        delayQueue.add(DelayElement.of("Delayed2", 800));
        delayQueue.add(DelayElement.of("Delayed3", 10000));
        delayQueue.add(DelayElement.of("Delayed4", 20000));
        long start = System.currentTimeMillis();
        System.out.println("delayQueue.remove() = " + delayQueue.remove());
        System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
    }

    /**
     * poll 方法如果首元素没有到达延时时间，那么会返回 null。
     */
    @Test
    public void testPoll() {
        DelayQueue<DelayElement<String>> delayQueue = create();
        delayQueue.add(DelayElement.of("Delayed1", 1000));
        delayQueue.add(DelayElement.of("Delayed2", 800));
        delayQueue.add(DelayElement.of("Delayed3", 10000));
        delayQueue.add(DelayElement.of("Delayed4", 20000));
        long start = System.currentTimeMillis();
        System.out.println("delayQueue.poll() = " + delayQueue.poll());
        System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
    }

    /**
     * take 方法如果首元素没有到达延时时间，那么会阻塞。
     */
    @Test
    public void testTake() throws InterruptedException {
        DelayQueue<DelayElement<String>> delayQueue = create();
        delayQueue.add(DelayElement.of("Delayed1", 1000));
        delayQueue.add(DelayElement.of("Delayed2", 800));
        delayQueue.add(DelayElement.of("Delayed3", 10000));
        delayQueue.add(DelayElement.of("Delayed4", 20000));
        long start = System.currentTimeMillis();
        System.out.println("delayQueue.take() = " + delayQueue.take());
        System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
    }

    /**
     * 如果队列为空，那么 take 方法就会等到有元素被添加且达到延时时间。
     */
    @Test
    public void testTake2() throws InterruptedException {
        DelayQueue<DelayElement<String>> delayQueue = create();
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delayQueue.add(DelayElement.of("element", 2000));
        });
        long start = System.currentTimeMillis();
        System.out.println("delayQueue.take() = " + delayQueue.take().getDate());
        System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));
    }

    private static class DelayElement<E> implements Delayed {

        private E e;
        private final long expireTime;

        public DelayElement(E e, long delay) {
            this.e = e;
            this.expireTime = System.currentTimeMillis() + delay;
        }

        public static <T> DelayElement<T> of(T t, long delay) {
            return new DelayElement<>(t, delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = this.expireTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed delayedObject) {
            DelayElement that = (DelayElement) delayedObject;
            if (this.expireTime < that.getExpireTime()) {
                return -1;
            } else if (this.expireTime > that.getExpireTime()) {
                return 1;
            } else {
                return 0;
            }
        }

        public E getDate() {
            return e;
        }

        public long getExpireTime() {
            return expireTime;
        }
    }
}
