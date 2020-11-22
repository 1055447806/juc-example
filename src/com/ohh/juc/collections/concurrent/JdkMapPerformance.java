package com.ohh.juc.collections.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Map 集合压力测试
 */
public class JdkMapPerformance {

    public static void main(String[] args) throws InterruptedException {
//        testMulti();
        testSingle();
    }

    /**
     * 多线程情况下，测试 3 种 map 集合，共 6 种情况下的读/写时间。
     * <pre>
     * Start pressure testing the map[class java.util.Hashtable] use the threshold [5], retrieve = false
     * 2500000 element inserted/retrieved in 2141ms
     * 2500000 element inserted/retrieved in 1143ms
     * 2500000 element inserted/retrieved in 1140ms
     * 2500000 element inserted/retrieved in 1082ms
     * 2500000 element inserted/retrieved in 968ms
     * For the map[class java.util.Hashtable] the average time is 1294 ms
     * Start pressure testing the map[class java.util.Hashtable] use the threshold [5], retrieve = true
     * 2500000 element inserted/retrieved in 1750ms
     * 2500000 element inserted/retrieved in 1558ms
     * 2500000 element inserted/retrieved in 1535ms
     * 2500000 element inserted/retrieved in 1554ms
     * 2500000 element inserted/retrieved in 1530ms
     * For the map[class java.util.Hashtable] the average time is 1585 ms
     * Start pressure testing the map[class java.util.Collections$SynchronizedMap] use the threshold [5], retrieve = false
     * 2500000 element inserted/retrieved in 1120ms
     * 2500000 element inserted/retrieved in 991ms
     * 2500000 element inserted/retrieved in 1138ms
     * 2500000 element inserted/retrieved in 966ms
     * 2500000 element inserted/retrieved in 1127ms
     * For the map[class java.util.Collections$SynchronizedMap] the average time is 1068 ms
     * Start pressure testing the map[class java.util.Collections$SynchronizedMap] use the threshold [5], retrieve = true
     * 2500000 element inserted/retrieved in 1562ms
     * 2500000 element inserted/retrieved in 1541ms
     * 2500000 element inserted/retrieved in 1378ms
     * 2500000 element inserted/retrieved in 1531ms
     * 2500000 element inserted/retrieved in 1516ms
     * For the map[class java.util.Collections$SynchronizedMap] the average time is 1505 ms
     * Start pressure testing the map[class java.util.concurrent.ConcurrentHashMap] use the threshold [5], retrieve = false
     * 2500000 element inserted/retrieved in 681ms
     * 2500000 element inserted/retrieved in 556ms
     * 2500000 element inserted/retrieved in 397ms
     * 2500000 element inserted/retrieved in 524ms
     * 2500000 element inserted/retrieved in 415ms
     * For the map[class java.util.concurrent.ConcurrentHashMap] the average time is 514 ms
     * Start pressure testing the map[class java.util.concurrent.ConcurrentHashMap] use the threshold [5], retrieve = true
     * 2500000 element inserted/retrieved in 740ms
     * 2500000 element inserted/retrieved in 609ms
     * 2500000 element inserted/retrieved in 500ms
     * 2500000 element inserted/retrieved in 616ms
     * 2500000 element inserted/retrieved in 601ms
     * For the map[class java.util.concurrent.ConcurrentHashMap] the average time is 613 ms
     * </pre>
     */
    public static void testMulti() throws InterruptedException {
        // Hashtable
        pressureTest(new Hashtable<>(), 5, false);
        pressureTest(new Hashtable<>(), 5, true);
        // synchronizedMap
        pressureTest(Collections.synchronizedMap(new HashMap<>()), 5, false);
        pressureTest(Collections.synchronizedMap(new HashMap<>()), 5, true);
        // ConcurrentHashMap
        pressureTest(new ConcurrentHashMap<>(), 5, false);
        pressureTest(new ConcurrentHashMap<>(), 5, true);
    }

    /**
     * 单线程情况下，测试 4 种 map 集合，共 8 种情况下的读/写时间。
     * <pre>
     * Start pressure testing the map[class java.util.Hashtable] use the threshold [1], retrieve = false
     * 500000 element inserted/retrieved in 572ms
     * 500000 element inserted/retrieved in 369ms
     * 500000 element inserted/retrieved in 371ms
     * 500000 element inserted/retrieved in 375ms
     * 500000 element inserted/retrieved in 1361ms
     * For the map[class java.util.Hashtable] the average time is 609 ms
     * Start pressure testing the map[class java.util.Hashtable] use the threshold [1], retrieve = true
     * 500000 element inserted/retrieved in 697ms
     * 500000 element inserted/retrieved in 1482ms
     * 500000 element inserted/retrieved in 239ms
     * 500000 element inserted/retrieved in 334ms
     * 500000 element inserted/retrieved in 679ms
     * For the map[class java.util.Hashtable] the average time is 686 ms
     * Start pressure testing the map[class java.util.HashMap] use the threshold [1], retrieve = false
     * 500000 element inserted/retrieved in 237ms
     * 500000 element inserted/retrieved in 381ms
     * 500000 element inserted/retrieved in 172ms
     * 500000 element inserted/retrieved in 183ms
     * 500000 element inserted/retrieved in 203ms
     * For the map[class java.util.HashMap] the average time is 235 ms
     * Start pressure testing the map[class java.util.HashMap] use the threshold [1], retrieve = true
     * 500000 element inserted/retrieved in 367ms
     * 500000 element inserted/retrieved in 523ms
     * 500000 element inserted/retrieved in 317ms
     * 500000 element inserted/retrieved in 753ms
     * 500000 element inserted/retrieved in 208ms
     * For the map[class java.util.HashMap] the average time is 433 ms
     * Start pressure testing the map[class java.util.Collections$SynchronizedMap] use the threshold [1], retrieve = false
     * 500000 element inserted/retrieved in 166ms
     * 500000 element inserted/retrieved in 200ms
     * 500000 element inserted/retrieved in 395ms
     * 500000 element inserted/retrieved in 170ms
     * 500000 element inserted/retrieved in 455ms
     * For the map[class java.util.Collections$SynchronizedMap] the average time is 277 ms
     * Start pressure testing the map[class java.util.Collections$SynchronizedMap] use the threshold [1], retrieve = true
     * 500000 element inserted/retrieved in 261ms
     * 500000 element inserted/retrieved in 294ms
     * 500000 element inserted/retrieved in 454ms
     * 500000 element inserted/retrieved in 237ms
     * 500000 element inserted/retrieved in 226ms
     * For the map[class java.util.Collections$SynchronizedMap] the average time is 294 ms
     * Start pressure testing the map[class java.util.concurrent.ConcurrentHashMap] use the threshold [1], retrieve = false
     * 500000 element inserted/retrieved in 208ms
     * 500000 element inserted/retrieved in 340ms
     * 500000 element inserted/retrieved in 180ms
     * 500000 element inserted/retrieved in 191ms
     * 500000 element inserted/retrieved in 177ms
     * For the map[class java.util.concurrent.ConcurrentHashMap] the average time is 219 ms
     * Start pressure testing the map[class java.util.concurrent.ConcurrentHashMap] use the threshold [1], retrieve = true
     * 500000 element inserted/retrieved in 214ms
     * 500000 element inserted/retrieved in 256ms
     * 500000 element inserted/retrieved in 375ms
     * 500000 element inserted/retrieved in 450ms
     * 500000 element inserted/retrieved in 224ms
     * For the map[class java.util.concurrent.ConcurrentHashMap] the average time is 303 ms
     * </pre>
     */
    public static void testSingle() throws InterruptedException {
        // HashTable
        pressureTest(new Hashtable<>(), 1, false);
        pressureTest(new Hashtable<>(), 1, true);
        // HashMap
        pressureTest(new HashMap<>(), 1, false);
        pressureTest(new HashMap<>(), 1, true);
        // SynchronizedMap
        pressureTest(Collections.synchronizedMap(new HashMap<>()), 1, false);
        pressureTest(Collections.synchronizedMap(new HashMap<>()), 1, true);
        // ConcurrentHashMap
        pressureTest(new ConcurrentHashMap<>(), 1, false);
        pressureTest(new ConcurrentHashMap<>(), 1, true);
    }

    /**
     * 测试不同 Map 集合的读写性能，5 组测试用例，每组测试用例启动 threshold 数量的线程，
     * 每个线程处理 500000 个数据。
     *
     * @param map       要测试的集合
     * @param threshold 定义的每组测试用例的线程数量
     * @param retrieve  是否进行读操作，false 只插入数据，true 既读取数据又插入数据
     */
    private static void pressureTest(final Map<String, Integer> map, int threshold, final boolean retrieve) throws InterruptedException {
        System.out.println("Start pressure testing the map[" + map.getClass() + "] use the threshold [" + threshold + "], retrieve = " + retrieve);
        long totalTime = 0L;
        final int MAX_THRESHOLD = 500000;
        for (int i = 0; i < 5; i++) {
            long startTime = System.nanoTime();
            ExecutorService executorService = Executors.newFixedThreadPool(threshold);
            for (int j = 0; j < threshold; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < MAX_THRESHOLD; k++) {
                        Integer randomNumber = (int) Math.ceil(Math.random() * 600000);
                        if (retrieve) {
                            map.get(String.valueOf(randomNumber));
                        }
                        map.put(String.valueOf(randomNumber), randomNumber);
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.HOURS);
            long endTime = System.nanoTime();
            long period = (endTime - startTime) / 1000000L;
            System.out.println(threshold * MAX_THRESHOLD + " element inserted/retrieved in " + period + "ms");
            totalTime += period;
        }
        System.out.println("For the map[" + map.getClass() + "] the average time is " + (totalTime / 5) + " ms");
    }
}
