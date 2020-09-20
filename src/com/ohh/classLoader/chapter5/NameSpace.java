package com.ohh.classLoader.chapter5;

public class NameSpace {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = NameSpace.class.getClassLoader();
        final Class<?> aClass = classLoader.loadClass("java.lang.String");
        final Class<?> bClass = classLoader.loadClass("java.lang.String");
        System.out.println(aClass.hashCode());
        System.out.println(bClass.hashCode());
    }
}
