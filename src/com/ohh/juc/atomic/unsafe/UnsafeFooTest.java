package com.ohh.juc.atomic.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe 趣味测试
 */
public class UnsafeFooTest {
    private static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
        /*Simple simple = new Simple();
        System.out.println("simple.get() = " + simple.get());*/
        /*Simple simple = Simple.class.newInstance();*/
        /*Class.forName("com.ohh.jcu.atomic.UnsafeFooTest$Simple");*/
        Unsafe unsafe = getUnsafe();
        /*Simple simple = (Simple)unsafe.allocateInstance(Simple.class);
        System.out.println("simple.get() = " + simple.get());
        System.out.println("simple.getClass() = " + simple.getClass());
        System.out.println("simple.getClass().getClassLoader() = " + simple.getClass().getClassLoader());*/


        Guard guard = new Guard();
        guard.work();

        Field access_allowed = Guard.class.getDeclaredField("ACCESS_ALLOWED");
        unsafe.putInt(guard, unsafe.objectFieldOffset(access_allowed), 42);
        guard.work();
    }

    static class Guard {
        private int ACCESS_ALLOWED = 1;

        private boolean allow() {
            return 42 == ACCESS_ALLOWED;
        }

        public void work() {
            if (allow()) {
                System.out.println("I am working by allowed.");
            }
        }
    }

    static class Simple {
        private long l = 0;

        public Simple() {
            this.l = 1;
            System.out.println("=Simple=");
        }

        public long get() {
            return this.l;
        }
    }
}
