package com.ohh.classLoader.chapter1;

import java.util.Random;

public class ClassActiveUse {
    public static void main(String[] args) throws ClassNotFoundException {
//        new Obj();
//        System.out.println(Obj.salary);
//        System.out.println(Obj.person);
//        Obj.print();
//        Class.forName("com.ohh.classLoader.chapter1.Obj");
//        System.out.println(Child.age);
//        System.out.println(Child.salary);
//        Obj[] objs = new Obj[10];
//        System.out.println(Obj.salary2);
//        System.out.println(Obj.x);
    }
}

class Obj {
    static {
        System.out.println("Obj 被初始化");
    }

    public static int salary = 100000;
    public static final int salary2 = 200000; // 常量
    public static final int x = new Random().nextInt(100);

    public static void print() {
        System.out.println("Obj-print run");
    }
}

class Child extends Obj {
    static {
        System.out.println("Child 被初始化");
    }

    public static int age = 23;
}