package com.wgy.juc.collections.concurrent;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 性能测试
 */
public class ConcurrentListPerformaceTest {

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
     */
    private final static Map<String, List<Entry>> summary = new HashMap<>();

    /**
     * 性能测试结果：
     * => SynchronizedRandomAccessList
     * Entry{count=10, ms=6}
     * Entry{count=20, ms=4}
     * Entry{count=30, ms=4}
     * Entry{count=40, ms=5}
     * Entry{count=50, ms=8}
     * Entry{count=60, ms=12}
     * Entry{count=70, ms=22}
     * Entry{count=80, ms=23}
     * Entry{count=90, ms=21}
     * ==========================================
     * => ConcurrentLinkedQueue
     * Entry{count=10, ms=31}
     * Entry{count=20, ms=6}
     * Entry{count=30, ms=8}
     * Entry{count=40, ms=7}
     * Entry{count=50, ms=7}
     * Entry{count=60, ms=11}
     * Entry{count=70, ms=19}
     * Entry{count=80, ms=13}
     * Entry{count=90, ms=44}
     * ==========================================
     * => CopyOnWriteArrayList
     * Entry{count=10, ms=82}
     * Entry{count=20, ms=66}
     * Entry{count=30, ms=78}
     * Entry{count=40, ms=57}
     * Entry{count=50, ms=55}
     * Entry{count=60, ms=59}
     * Entry{count=70, ms=58}
     * Entry{count=80, ms=63}
     * Entry{count=90, ms=55}
     * ==========================================
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 10; i < 100; i+=10) {
            pressureTest( new ConcurrentLinkedQueue<>(),i);
            pressureTest( new CopyOnWriteArrayList<>(),i);
            pressureTest(Collections.synchronizedList(new ArrayList<>()),i);

        }
        summary.forEach((k, v) -> {
            System.out.println("=> " + k);
            v.forEach(System.out::println);
            System.out.println("==========================================");
        });
    }

    private static void pressureTest(final Collection<String> list, int threshold) throws InterruptedException {
        System.out.println("Start pressure testing the map[" + list.getClass() + "] use the threshold [" + threshold + "]");
        long totalTime = 0L;
        final int MAX_THRESHOLD = 10000;
        for (int i = 0; i < 5; i++) {
            final AtomicInteger counter = new AtomicInteger(0);
            list.clear();
            long startTime = System.nanoTime();
            ExecutorService executorService = Executors.newFixedThreadPool(threshold);
            for (int j = 0; j < threshold; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < MAX_THRESHOLD && counter.getAndIncrement() < MAX_THRESHOLD; k++) {
                        Integer randomNumber = (int) Math.ceil(Math.random() * 600000);
                        list.add(String.valueOf(randomNumber));
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.HOURS);
            long endTime = System.nanoTime();
            long period = (endTime - startTime) / 1000000L;
            System.out.println(MAX_THRESHOLD + " element added in " + period + "ms");
            totalTime += period;
        }
        List<Entry> entries = summary.get(list.getClass().getSimpleName());
        if (entries == null) {
            entries = new ArrayList<>();
            summary.put(list.getClass().getSimpleName(), entries);
        }
        entries.add(new Entry(threshold, totalTime / 5));
        System.out.println("For the map[" + list.getClass() + "] the average time is " + (totalTime / 5) + " ms");

    }
}
