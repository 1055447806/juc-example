package com.wgy.concurrent2.chapter8future;

/**
 * Future 设计模式
 * <p>
 * 在很多时候，任务的执行时间过长，等待任务的响应结果会造成程序的阻塞，
 * 此时可以通过 Future 设计模式进行优化，通过启动一个新的线程来执行任务，
 * 并把响应的结果保存起来，然后在未来的某个时间点，去获取并使用这个结果。
 *
 * @author Gary
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 使用 Future 设计模式提交任务 task
        Future<String> future = new FutureService().submit(Main::task, System.out::println);
        System.out.println("提交了任务，等待结果");
        System.out.println("结果是：" + future.get());
    }

    /**
     * 这个任务会执行 10 秒钟，并返回字符串 FINISH
     *
     * @return 执行结果
     */
    public static String task() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "FINISH";
    }
}
