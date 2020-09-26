package com.ohh.juc.executor.completionService;

import java.util.concurrent.*;

/**
 * 使用 ExecutorCompletionService 的 submit(Runnable,V) 方法模拟 submit(Callable) 方法。
 */
public class CompletionServiceExample3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Event> executorCompletionService = new ExecutorCompletionService<>(executorService);
        final Event event = new Event();
        executorCompletionService.submit(new MyTask(event), event);
        System.out.println(executorCompletionService.take().get().getResult());
    }

    /**
     * MyTask 对 event 的 result 进行赋值。
     */
    private static class MyTask implements Runnable {

        private final Event event;

        private MyTask(Event event) {
            this.event = event;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            event.setResult("successful.");
        }
    }


    private static class Event {

        private String result;

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }
}
