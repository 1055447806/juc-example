package com.ohh.classLoader.chapter5;

public class RuntimePackage {
    public static void main(String[] args) throws Exception {
        SimpleClassLoader simpleClassLoader = new SimpleClassLoader();
        final Class<?> aClass = simpleClassLoader.loadClass("com.ohh.classLoader.chapter5.SimpleObject");
        // java.lang.ClassCastException:
        // com.ohh.classLoader.chapter5.SimpleObject cannot be cast to com.ohh.classLoader.chapter5.SimpleObject
        SimpleObject simpleObject = (SimpleObject) aClass.newInstance();
    }

}