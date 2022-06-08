package com.wgy.classLoader.chapter5;

public class SimpleClassLoaderTest {
    public static void main(String[] args) throws Exception {
        SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
        final Class<?> aClass = simpleClassLoader.loadClass("com.wgy.classLoader.chapter5.SimpleObject");
        System.out.println(aClass.getClassLoader());
    }
}
