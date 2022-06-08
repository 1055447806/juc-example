package com.wgy.concurrent1.chapter5;

import java.util.stream.IntStream;

/**
 * join 方法的简单使用。
 */
public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {

        // t1 输出 0-199 的数
        Thread t1 = new Thread(
                () -> IntStream.range(0, 200).forEach(i -> {
                    System.out.println("t1 ->" + i);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );

        // t2 输出 0-99后 调用 t1.join 方法，然后再输出 100-199
        Thread t2 = new Thread(
                () -> {
                    IntStream.range(0, 100).forEach(i -> {
                        System.out.println("t2 ->" + i);
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                    try {
                        t1.start();
                        t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    IntStream.range(100, 200).forEach(i -> {
                        System.out.println("t2 ->" + i);
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
        );

        t2.start();
    }
}
