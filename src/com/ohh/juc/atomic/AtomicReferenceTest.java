package com.ohh.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    public static void main(String[] args) {
        AtomicReference<Simple> atomic = new AtomicReference<>(new Simple("Gary", 20));
        System.out.println("atomic.get() = " + atomic.get());
//        boolean success = atomic.compareAndSet(new Simple("Wu", 18), new Simple("ABC", 22));
        boolean success = atomic.compareAndSet(atomic.get(), new Simple("ABC", 22));
        System.out.println("success = " + success);
    }

    static class Simple {
        private String name;
        private int age;

        public Simple(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Simple{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
