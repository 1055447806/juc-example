package com.ohh.juc.executor.scheduledExcutorService;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 */
public class TimerScheduler {
    public static void main(String[] args) {
        Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("=====" + System.currentTimeMillis());
            }
        };
        timer.schedule(timerTask, 1000, 2000);
    }
}
