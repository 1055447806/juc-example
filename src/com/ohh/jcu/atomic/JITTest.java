package com.ohh.jcu.atomic;

/**
 * just in time
 */
public class JITTest {
    private static volatile boolean init = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override
            public void run() {
                while (!init) {
                    // 在某些jdk中，由于此处while循环体为空，jvm会将while循环优化为while(true){}
                }
            }
        }.start();

        Thread.sleep(1000L);

        new Thread(){
            @Override
            public void run() {
                init = true;
                System.out.println("set init to true.");
            }
        }.start();
    }
}
