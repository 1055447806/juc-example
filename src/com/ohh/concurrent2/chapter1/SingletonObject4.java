package com.ohh.concurrent2.chapter1;

public class SingletonObject4 {

    private static SingletonObject4 instance;

    private SingletonObject4() {
        //empty
    }

    public static SingletonObject4 getInstance() {
        if (null == instance) {
            synchronized (SingletonObject4.class) {
                if (null == instance) {
                    instance = new SingletonObject4();
                }
            }
        }

        return instance;
    }
}
