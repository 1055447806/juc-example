package com.wgy.classLoader.chapter4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DecryptClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
//        //非解密类加载器
//        MyClassLoader myClassLoader = new MyClassLoader();
//        myClassLoader.setDir("C:\\classLoader\\cl3");
//        final Class<?> aClass = myClassLoader.loadClass("com.ohh.classLoader.chapter3.MyObject");


        //解密类加载器
        DecryptClassLoader decryptClassLoader = new DecryptClassLoader();
        final Class<?> aClass = decryptClassLoader.loadClass("com.wgy.classLoader.chapter3.MyObject");

        //测试
        final Object object = aClass.newInstance();
        final Method hello = aClass.getMethod("hello");
        final Object result = hello.invoke(object);
        System.out.println(result);
    }
}
