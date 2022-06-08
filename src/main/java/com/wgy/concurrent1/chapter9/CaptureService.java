package com.wgy.concurrent1.chapter9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CaptureService {

    private static final LinkedList<Control> CONTROLS = new LinkedList<>();

    private static final int MAX_WORKER = 5;

    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();
        Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .map(CaptureService::createCaptureThread)
                .forEach(t -> {
                    t.start();
                    worker.add(t);
                });
        worker.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Optional.of("All the capture work finished").ifPresent(System.out::println);
    }

    private static Thread createCaptureThread(String name) {
        return new Thread(() -> {
            Optional.of("The worker [" + Thread.currentThread().getName() + "] is start.")
                    .ifPresent(System.out::println);
            synchronized (CONTROLS) {
                while (CONTROLS.size() >= MAX_WORKER) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROLS.addLast(new Control());
                System.out.println(CONTROLS.size());
            }

            Optional.of("The worker [" + Thread.currentThread().getName() + "] is running...").ifPresent(System.out::println);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (CONTROLS) {
                Optional.of("The worker [" + Thread.currentThread().getName() + "] is finished.").ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        }, name);
    }

    private static class Control {
    }
}
