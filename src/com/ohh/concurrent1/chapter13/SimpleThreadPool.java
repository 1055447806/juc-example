package com.ohh.concurrent1.chapter13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleThreadPool extends Thread {

    private int min;
    public static final int DEFAULT_MIN = 4;
    private int active;
    public static final int DEFAULT_ACTIVE = 8;
    private int max;
    public static final int DEFAULT_MAX = 12;

    private final int taskQueueMaxSize; // 最大任务数
    public static final int DEFAULT_TASK_QUEUE_MAX_SIZE = 2000;

    private final DiscardPolicy discardPolicy; // 拒绝策略
    public static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("Discard this task.");
    };

    private static volatile int seq = 0;
    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";
    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>(); // 任务等待队列 FIFO
    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>(); // 执行线程集合
    private volatile boolean isDestroy = false;

    SimpleThreadPool() {
        this(DEFAULT_MIN, DEFAULT_ACTIVE, DEFAULT_MAX, DEFAULT_TASK_QUEUE_MAX_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int taskQueueMaxSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.taskQueueMaxSize = taskQueueMaxSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        create(min);
        this.start();
    }

    public void submit(Runnable runnable) {
        if (isDestroy)
            throw new IllegalStateException("the thread pool already destroy and not allow submit task.");
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() >= taskQueueMaxSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public void shutdown() {
        this.isDestroy = true;
        while (!TASK_QUEUE.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\033[1;46;32m" + THREAD_QUEUE.size() + "\033[0m");
        while (THREAD_QUEUE.size() != 0) {
            release(THREAD_QUEUE.size());
        }
        System.out.println("The thread pool disposed");
    }

    /**
     * 每隔5秒钟输出线程池信息
     */
    @Override
    public void run() {
        while (!isDestroy) {
            System.out.printf("Pool#Min:%d, Active:%d, Max:%d, Current:%d, QueueSize:%d\r\n",
                    min, active, max, THREAD_QUEUE.size(), TASK_QUEUE.size());
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (THREAD_QUEUE.size() < max && max < TASK_QUEUE.size()) {
                create(max - THREAD_QUEUE.size());
                System.out.println("The pool incremented to max.");
            } else if (THREAD_QUEUE.size() < active && active < TASK_QUEUE.size()) {
                create(active - THREAD_QUEUE.size());
                System.out.println("The pool incremented to active.");
            } else if (TASK_QUEUE.size() < min && min < THREAD_QUEUE.size()) {
                release(THREAD_QUEUE.size() - min);
                System.out.println("The pool release to min.");
            } else if (TASK_QUEUE.size() < active && active < THREAD_QUEUE.size()) {
                release(THREAD_QUEUE.size() - active);
                System.out.println("The pool release to active.");
            }
        }
    }

    private void create(int targetSize) {
        for (int i = 0; i < targetSize; i++) {
            createWorkTask();
        }
    }

    private void createWorkTask() {
        WorkerTask task = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    /**
     * 批量释放线程
     *
     * @param targetSize
     */
    private void release(int targetSize) {
        synchronized (TASK_QUEUE) {
            int releaseSize = 0;
            Iterator<WorkerTask> it = THREAD_QUEUE.iterator();
            while (it.hasNext()) {
                if (releaseSize >= targetSize) {
                    return;
                }
                if (releaseWorkTask(it)) {
                    releaseSize++;
                }
            }
        }
    }

    /**
     * 释放处于waiting状态的线程
     *
     * @param it
     * @return
     */
    private boolean releaseWorkTask(Iterator<WorkerTask> it) {
        WorkerTask task = it.next();
        if (task.getTaskState() == TaskState.BLOCKED) {
            task.interrupt();
            it.remove();
            System.out.println("\033[1;46;32m" + Thread.currentThread() + "release\033[0m");
            return true;
        }
        return false;
    }

    public int getMin() {
        return min;
    }

    public int getActive() {
        return active;
    }

    public int getMax() {
        return max;
    }

    public int getSize() {
        return THREAD_QUEUE.size();
    }

    public int getTaskQueueMaxSize() {
        return this.taskQueueMaxSize;
    }

    public boolean isDestroy() {
        return this.isDestroy;
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy {
        void discard();
    }

    /**
     * 工作线程状态
     */
    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    /**
     * 工作线程
     */
    private static class WorkerTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    taskState = TaskState.RUNNING;
                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    runnable.run();
                }
                taskState = TaskState.FREE;
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }

    /**
     * 测试线程池
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        IntStream.rangeClosed(0, 40).forEach(i ->
                threadPool.submit(() -> {
                    System.out.println("The runnable " + i + " be serviced by " + Thread.currentThread() + " start");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("The runnable " + i + " be serviced by " + Thread.currentThread() + " finished ");
                })
        );

        Thread.sleep(5000);
        threadPool.shutdown();

        while (true) {
            System.out.println("\033[1;46;32mactiveGroupCount:" + GROUP.activeCount() + "\033[0m");
            Thread[] threads = new Thread[GROUP.activeCount()];
            GROUP.enumerate(threads);
            for (Thread thread : threads) {
                System.out.println(thread);
            }
            Thread.sleep(1000);
        }
    }
}