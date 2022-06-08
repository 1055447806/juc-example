package com.wgy.juc.executor.scheduledExcutorService;


import java.util.Timer;
import java.util.TimerTask;

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
