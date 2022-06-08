package com.wgy.juc.collections.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentSkipListMapVsConcurrentHashMap {

    /**
     * 用来存放测试结果的键值对
     * threshold 线程数
     * ms 时间
     */
    static class Entry {
        int threshold;
        long ms;

        public Entry(int threshold, long ms) {
            this.threshold = threshold;
            this.ms = ms;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "count=" + threshold +
                    ", ms=" + ms +
                    '}';
        }
    }

    /**
     * 用来存放测试结果的集合
     * 集合中有两个结果集，
     * 一个是 ConcurrentHashMap 的结果集，
     * 一个是 ConcurrentSkipListMap 的结果集
     */
    private final static Map<Class<?>, List<Entry>> summary = new HashMap<Class<?>, List<Entry>>() {
        {
            put(ConcurrentHashMap.class, new ArrayList<>());
            put(ConcurrentSkipListMap.class, new ArrayList<>());
        }
    };

    /**
     * 测试 ConcurrentHashMap 和 ConcurrentSkipListMap 在多线程情况下处理数据的性能
     * 线程数量从分别为 10，20，30，40，50，60，70，80，90，100
     * 测试结果为：
     * k.getSimpleName() = ConcurrentHashMap
     * Entry{count=10, ms=501}
     * Entry{count=20, ms=344}
     * Entry{count=30, ms=284}
     * Entry{count=40, ms=132}
     * Entry{count=50, ms=162}
     * Entry{count=60, ms=200}
     * Entry{count=70, ms=124}
     * Entry{count=80, ms=112}
     * Entry{count=90, ms=125}
     * ==========================================
     * k.getSimpleName() = ConcurrentSkipListMap
     * Entry{count=10, ms=958}
     * Entry{count=20, ms=986}
     * Entry{count=30, ms=657}
     * Entry{count=40, ms=726}
     * Entry{count=50, ms=875}
     * Entry{count=60, ms=734}
     * Entry{count=70, ms=562}
     * Entry{count=80, ms=526}
     * Entry{count=90, ms=538}
     * ==========================================
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 10; i < 100; i += 10) {
            pressureTest(new ConcurrentHashMap<>(), i);
            pressureTest(new ConcurrentSkipListMap<>(), i);
        }
        summary.forEach((k, v) -> {
            System.out.println("k.getSimpleName() = " + k.getSimpleName());
            v.forEach(System.out::println);
            System.out.println("==========================================");
        });
    }

    /**
     * 测试不同 Map 集合的读写性能，5 组测试用例，每组测试用例启动 threshold 数量的线程，
     * 每个线程处理 500000 个数据。
     *
     * @param map       要测试的集合
     * @param threshold 定义的每组测试用例的线程数量
     */
    private static void pressureTest(final Map<String, Integer> map, int threshold) throws InterruptedException {
        System.out.println("Start pressure testing the map[" + map.getClass() + "] use the threshold [" + threshold + "]");
        long totalTime = 0L;
        final int MAX_THRESHOLD = 500000;
        for (int i = 0; i < 5; i++) {
            final AtomicInteger counter = new AtomicInteger(0);
            map.clear();
            long startTime = System.nanoTime();
            ExecutorService executorService = Executors.newFixedThreadPool(threshold);
            for (int j = 0; j < threshold; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < MAX_THRESHOLD && counter.getAndIncrement() < MAX_THRESHOLD; k++) {
                        Integer randomNumber = (int) Math.ceil(Math.random() * 600000);
                        map.get(String.valueOf(randomNumber));
                        map.put(String.valueOf(randomNumber), randomNumber);
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.HOURS);
            long endTime = System.nanoTime();
            long period = (endTime - startTime) / 1000000L;
            System.out.println(MAX_THRESHOLD + " element inserted/retrieved in " + period + "ms");
            totalTime += period;
        }
        summary.get(map.getClass()).add(new Entry(threshold, (totalTime / 5)));
        System.out.println("For the map[" + map.getClass() + "] the average time is " + (totalTime / 5) + " ms");
    }
}