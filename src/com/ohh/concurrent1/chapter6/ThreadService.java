package com.ohh.concurrent1.chapter6;

public class ThreadService {

    private Thread executeThread;

    private boolean finished = false;

    /**
     * 调用execute()方法, 会创建一个执行线程executeThread,
     * 在执行线程中, 包含一个守护线程runner,
     *
     * @param task 目标任务
     */
    public void execute(Runnable task) {

        executeThread = new Thread() {
            @Override
            public void run() {
                Thread runner = new Thread(task);
                runner.setDaemon(true);
                runner.start();
                try {
                    runner.join();
                    finished = true;
                } catch (InterruptedException e) {
                    System.out.println("runner线程被中断了");
                }
            }
        };

        executeThread.start();
    }

    public void shutdown(long millis) {
        long currentTime = System.currentTimeMillis();
        while (!finished) {
            if (System.currentTimeMillis() - currentTime >= millis) {
                executeThread.interrupt();
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
