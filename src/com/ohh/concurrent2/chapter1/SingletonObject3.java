package com.ohh.concurrent2.chapter1;

public class SingletonObject3 {

    private static SingletonObject3 instance;

    private SingletonObject3() {
        //empty
    }

    public synchronized static SingletonObject3 getInstance() {
        if (null == instance)
            return new SingletonObject3();
        return instance;
    }
}
