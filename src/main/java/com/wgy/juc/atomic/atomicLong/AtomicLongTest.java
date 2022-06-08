package com.wgy.juc.atomic.atomicLong;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

public class AtomicLongTest {
    @Test
    public void testCreate() {
        AtomicLong l = new AtomicLong(100);
        assertEquals(100L, l.get());
    }
}
