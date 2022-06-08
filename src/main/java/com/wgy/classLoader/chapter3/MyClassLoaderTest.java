package com.wgy.classLoader.chapter3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader myClassLoader = new MyClassLoader("myClassLoader");
        final Class<?> myObject = myClassLoader.loadClass("com.wgy.classLoader.chapter3.MyObject");
        System.out.println(myObject);
        Object object = myObject.newInstance();
        Method method = myObject.getMethod("hello");
        final Object result = method.invoke(object);
        System.out.println(result);
    }
}
