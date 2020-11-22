package com.ohh.juc.collections.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 思想：
 * 对读不加锁，对写加锁，
 * 写数据时，是在一个新的数组中，完成时使用新数组替换原数组
 */
public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        final CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    }
}
