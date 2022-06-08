package com.wgy.juc.atomic.atomicIntegerArray;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static org.junit.Assert.assertEquals;

/**
 *  AtomicLongArray 和 AtomicReferenceArray 与之相同
 */
public class AtomicIntegerArrayTest {

    @Test
    public void testCreate() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(array.length(), 10);
    }

    @Test
    public void testCreate2() {
        int[] arr = new int[10];
        AtomicIntegerArray array = new AtomicIntegerArray(arr);
        assertEquals(array.length(), 10);
    }

    @Test
    public void testGet() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(0, array.get(5));
    }

    @Test
    public void testSet() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(0, array.get(5));
        array.set(5, 5);
        assertEquals(5, array.get(5));
    }

    @Test
    public void testGetAndSet() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(0, array.getAndSet(5, 5));
        assertEquals(5, array.getAndSet(5, 5));
    }

}
