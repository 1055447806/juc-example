package com.ohh.juc.atomic.atomicIntegerFieldUpdater;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicXXXFieldUpdaterErrorUseCasesTest {

    /**
     * java.lang.RuntimeException: java.lang.IllegalAccessException:
     * Class com.ohh.jcu.atomic.AtomicXXXFieldUpdaterErrorUseCasesTest can not access a member of class com.ohh.jcu.atomic.TestMe with modifiers "private volatile"
     * 如果是在同一个类中定义并使用， 也可以为private或protected
     */
    @Test
    public void testPrivateOrProtectedFieldAccessError() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "privateInt");
        TestMe me = new TestMe();
        updater.compareAndSet(me, 0, 1);
    }

    /**
     * java.lang.ClassCastException
     */
    @Test
    public void testTargetObjectIsNull() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe me = null;
        updater.compareAndSet(me, 0, 1);
    }

    /**
     * java.lang.RuntimeException: java.lang.NoSuchFieldException: noSuchField
     */
    @Test
    public void testFieldNameInvalid() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "noSuchField");
        TestMe me = new TestMe();
        updater.compareAndSet(me, 0, 1);
    }

    /**
     * java.lang.IllegalArgumentException: Must be integer type
     * int => Integer
     */
    @Test
    public void testFieldTypeInvalid() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "anInteger");
        TestMe me = new TestMe();
        updater.compareAndSet(me, 0, 1);
    }

    /**
     * java.lang.ClassCastException
     * Integer => Long
     */
    @Test
    public void testFieldTypeInvalid2() {
        AtomicReferenceFieldUpdater<TestMe, Long> updater = AtomicReferenceFieldUpdater.newUpdater(TestMe.class, Long.class, "anInteger");
        TestMe me = new TestMe();
        updater.compareAndSet(me, null, 1L);
    }

    /**
     * java.lang.IllegalArgumentException: Must be volatile type
     */
    @Test
    public void testFieldIsNotVolatile() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "notVolatileInt");
        TestMe me = new TestMe();
        updater.compareAndSet(me, 0, 1);
    }

    /**
     * java.lang.ClassCastException
     */
    @Test
    public void testParameterTypeError() {
        AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        updater.compareAndSet(new Object(), 0, 1);
    }
}

class TestMe {
    private volatile int privateInt;
    volatile int i;
    volatile Integer anInteger;
    int notVolatileInt;
}
