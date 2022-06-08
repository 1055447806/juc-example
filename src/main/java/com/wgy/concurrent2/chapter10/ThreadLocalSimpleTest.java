package com.wgy.concurrent2.chapter10;

public class ThreadLocalSimpleTest {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "NoName";
        }
    };

    public static void main(String[] args) {
        threadLocal.set("Gary");
        System.out.println(threadLocal.get());
    }

}
