package com.ohh.juc.collections.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 需要注意的一点是：
 * ConcurrentLinkedQueue 的 size 方法是遍历队列求长度，
 * ConcurrentLinkedQueue 的 isEmpty 方法只看头元素是否存在。
 */
public class ConcurrentLinkedQueueExample {

    /**
     * 使用 size 方法
     * 执行时间 35948
     */
    @Test
    public void testSize(){
        final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 100_000; i++) {
            queue.offer(System.nanoTime());
        }
        System.out.println("offer done.");
        long startTime = System.currentTimeMillis();
        while(queue.size()>0){
            queue.poll();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    /**
     * 使用 isEmpty 方法
     * 执行时间 22
     */
    @Test
    public void testIsEmpty(){
        final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 100_000; i++) {
            queue.offer(System.nanoTime());
        }
        System.out.println("offer done.");
        long startTime = System.currentTimeMillis();
        while(!queue.isEmpty()){
            queue.poll();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static void handleText(String s) {
        // 效率低
        if (null != s && !"".equals(s)) {

        }

        // 效率高
        if (null != s && s.length() > 0) {

        }

        // 效率高
        if (null != s && !s.isEmpty()) {

        }
    }
}
