package com.wgy.juc.collections.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample {

    public static <K, V> ConcurrentSkipListMap<K, V> create() {
        return new ConcurrentSkipListMap<>();
    }

    @Test
    public void testCeiling() {
        ConcurrentSkipListMap<Integer, String> map = ConcurrentSkipListMapExample.create();
        map.put(1, "Scala");
        map.put(5, "Java");
        map.put(10, "Clojure");
        System.out.println("map.ceilingKey(2) = " + map.ceilingKey(2));
        System.out.println("map.ceilingEntry(2) = " + map.ceilingEntry(2));
    }

    @Test
    public void testFloor() {
        ConcurrentSkipListMap<Integer, String> map = create();
        map.put(1, "Scala");
        map.put(5, "Java");
        map.put(10, "Clojure");
        System.out.println("map.floorKey(2) = " + map.floorKey(2));
        System.out.println("map.floorEntry(2) = " + map.floorEntry(2));
    }

    @Test
    public void testFirstAndLast() {
        ConcurrentSkipListMap<Integer, String> map = create();
        map.put(1, "Scala");
        map.put(5, "Java");
        map.put(10, "Clojure");
        System.out.println("map.firstKey() = " + map.firstKey());
        System.out.println("map.firstEntry() = " + map.firstEntry());
        System.out.println("map.lastKey() = " + map.lastKey());
        System.out.println("map.lastEntry() = " + map.lastEntry());
    }

    /**
     * merge 方法对新值和旧值做计算并返回
     */
    @Test
    public void testMerge() {
        ConcurrentSkipListMap<Integer, String> map = create();
        map.put(1, "Scala");
        map.put(5, "Java");
        map.put(10, "Clojure");
        map.merge(1, "C++", (oldValue, newValue) -> oldValue + newValue);
        System.out.println("map.firstEntry() = " + map.firstEntry());
    }

    /**
     * compute 方法对键和值做计算并返回
     */
    @Test
    public void testCompute() {
        ConcurrentSkipListMap<Integer, String> map = create();
        map.put(1, "Scala");
        map.put(5, "Java");
        map.put(10, "Clojure");
        map.compute(1, (k, v) -> String.format("Hello, %d, %s", k, v));
        System.out.println("map.firstEntry() = " + map.firstEntry());
    }
}
