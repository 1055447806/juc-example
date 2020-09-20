package com.ohh.concurrent2.chapter1;

public class SingletonObject2 {

    private static SingletonObject2 instance;

    private SingletonObject2() {
        //empty
    }

    public static SingletonObject2 getInstance() {
        if (null == instance)
            return new SingletonObject2();
        return instance;
    }
}
