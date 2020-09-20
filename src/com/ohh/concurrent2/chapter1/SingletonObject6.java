package com.ohh.concurrent2.chapter1;

public class SingletonObject6 {

    private SingletonObject6() {
        //empty
    }

    private static class InstanceHolder {
        private static final SingletonObject6 instance = new SingletonObject6();
    }

    public static SingletonObject6 getInstance() {
        return InstanceHolder.instance;
    }
}
