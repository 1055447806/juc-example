package com.ohh.juc.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorServiceExample4 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
    }

    private static void testInvokeAny() {

    }
}
