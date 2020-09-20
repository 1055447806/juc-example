package com.ohh.concurrent2.chapter1;

public class SingletonObject5 {

    private static volatile SingletonObject5 instance;

    private SingletonObject5() {
        //empty
    }

    public synchronized static SingletonObject5 getInstance() {
        if (null == instance) {
            synchronized (SingletonObject5.class) {
                if (null == instance) {
                    instance = new SingletonObject5();
                }
            }
        }

        return instance;
    }
}
