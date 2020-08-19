package com.ohh.juc.utils.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ForkJoinRecursiveAction {
    private static final int MAX_THRESHOLD = 3;
    private static final AtomicInteger SUM = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        CalculateRecursiveAction calculateRecursiveAction = new CalculateRecursiveAction(0, 10);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(calculateRecursiveAction);
        forkJoinPool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(SUM.get());
    }

    private static class CalculateRecursiveAction extends RecursiveAction {
        private final int start;
        private final int end;

        private CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculateRecursiveAction leftAction = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction rightAction = new CalculateRecursiveAction(middle + 1, end);
                leftAction.fork();
                rightAction.fork();
            }
        }
    }
}
