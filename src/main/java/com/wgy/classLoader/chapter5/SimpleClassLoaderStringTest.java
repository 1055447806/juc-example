package com.wgy.classLoader.chapter5;

public class SimpleClassLoaderStringTest {
    public static void main(String[] args) throws Exception {
        SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
        // java.lang.SecurityException: Prohibited package name: java.lang
        final Class<?> aClass = simpleClassLoader.loadClass("java.lang.String");
        System.out.println(aClass.getClassLoader());
    }
}
