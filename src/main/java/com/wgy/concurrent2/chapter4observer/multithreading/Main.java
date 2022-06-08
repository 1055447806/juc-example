package com.wgy.concurrent2.chapter4observer.multithreading;

import java.util.Arrays;
import java.util.List;

/**
 * Observer 模式在多线程环境下的简单应用
 * <p>
 * 模拟数据库查询操作，按 id 执行多次查询，每次查询启动一个线程，并对其进行监听。
 *
 * @author Gary
 */
public class Main {
    public static void main(String[] args) {
        // 需要查询的 id 集合
        List<String> ids = Arrays.asList("1", "2", "3", "4", "5");
        // 线程监听类实例
        ThreadLifeCycleObserver threadLifeCycleObserver = new ThreadLifeCycleObserver();
        // 对 id 进行遍历，并创建线程进行查询
        ids.forEach(id ->
                new Thread(new ObservableRunnable(threadLifeCycleObserver) {
                    @Override
                    protected void execute() throws InterruptedException {
                        System.out.println(String.format("Querying id %s", id));
                        Thread.sleep(1000);
                        /*
                        // 使程序出错，并对 Observer 发送异常状态通知
                        int i = 1 / 0;
                        */
                    }
                }, id).start()
        );
    }
}