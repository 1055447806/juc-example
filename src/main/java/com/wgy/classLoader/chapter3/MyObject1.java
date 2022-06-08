package com.wgy.classLoader.chapter3;

public class MyObject1 {
    static {
        System.out.println("MyObject static block.");
    }

    public String hello() {
        return "hello world";
    }
}
