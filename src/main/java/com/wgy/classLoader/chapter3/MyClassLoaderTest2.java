package com.wgy.classLoader.chapter3;

public class MyClassLoaderTest2 {
    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader myClassLoader1 = new MyClassLoader("MyClassLoader1");
        MyClassLoader myClassLoader2 = new MyClassLoader("MyClassLoader2", myClassLoader1);
        myClassLoader2.setDir("C:/classLoader/cl2");

        // 父委托机制
        final Class<?> aClass = myClassLoader2.loadClass("com.wgy.classLoader.chapter3.MyObject");
        System.out.println(aClass);
        System.out.println(((MyClassLoader) aClass.getClassLoader()).getClassLoaderName());

        System.out.println("------------");
        // 命名空间
        MyClassLoader myClassLoader3 = new MyClassLoader("MyClassLoader3");
        myClassLoader3.setDir("C:/classLoader/cl2");
        final Class<?> aClass3 = myClassLoader3.loadClass("com.wgy.classLoader.chapter3.MyObject");
        System.out.println("aClass.hashCode:" + aClass.hashCode());
        System.out.println("aClass3.hashCode:" + aClass3.hashCode());
    }
}