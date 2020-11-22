package com.ohh.juc.collections.blocking;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinkedTransferQueueExample {

    public static <T> LinkedTransferQueue<T> create() {
        return new LinkedTransferQueue<>();
    }

    /**
     * tryTransfer 方法如果没有消费者正在消费，那么就会返回 false，
     * 并且元素不会被添加到队列中。
     */
    @Test
    public void testTryTransfer() {
        LinkedTransferQueue<String> queue = create();
        boolean result = queue.tryTransfer("Java");
        System.out.println("result = " + result);
    }

    /**
     * transfer 方法会阻塞直到有消费者来消费。
     */
    @Test
    public void testTransfer() throws InterruptedException {
        LinkedTransferQueue<String> queue = create();
        Executors.newSingleThreadScheduledExecutor().schedule((Callable<String>) queue::poll, 5, TimeUnit.SECONDS);
        long start = System.currentTimeMillis();
        queue.transfer("Java");
        System.out.println("System.currentTimeMillis() - start = " + (System.currentTimeMillis() - start));
    }

    /**
     * getWaitingConsumerCount 方法获取正在等待的消费者数量
     * hasWaitingConsumer 获取是否有消费者正在等待
     */
    @Test
    public void testTransfer2() throws InterruptedException {
        LinkedTransferQueue<String> queue = create();
        System.out.println("queue.getWaitingConsumerCount() = " + queue.getWaitingConsumerCount());
        System.out.println("queue.hasWaitingConsumer() = " + queue.hasWaitingConsumer());
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 生成5个消费者
        IntStream.range(0, 5).mapToObj(i -> (Callable<String>) () -> {
            try {
                return queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).forEach(executorService::submit);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("queue.getWaitingConsumerCount() = " + queue.getWaitingConsumerCount());
        System.out.println("queue.hasWaitingConsumer() = " + queue.hasWaitingConsumer());
        // 生成5个生产者
        IntStream.range(0, 5).mapToObj(i -> (Runnable) () -> queue.add(String.valueOf(i))).forEach(executorService::submit);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("queue.getWaitingConsumerCount() = " + queue.getWaitingConsumerCount());
        System.out.println("queue.hasWaitingConsumer() = " + queue.hasWaitingConsumer());
    }

    /**
     * linked 不能放 null 值
     */
    @Test
    public void testAdd() {
        LinkedTransferQueue<String> queue = create();
        queue.add(null);
    }
}
