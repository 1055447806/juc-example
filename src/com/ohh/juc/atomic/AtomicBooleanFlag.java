package com.ohh.juc.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanFlag {
    /**
     * 使用 AtomicBoolean 替代 加volatile关键字的boolean
     */
     private static final AtomicBoolean flag = new AtomicBoolean(true);

     public static void main(String[] args) throws InterruptedException {
         new Thread(()->{
             while (flag.get()) {

             }
         }).start();
         Thread.sleep(2000);
         flag.set(false);
         System.out.println("set the flag to false");
     }
}
