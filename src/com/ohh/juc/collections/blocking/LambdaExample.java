package com.ohh.juc.collections.blocking;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * lambda小技巧：
 * 利用 lambda 隐藏对象所有细节，只暴露一个方法
 */
public class LambdaExample {

    @Test
    public void testLambda() throws IOException {
        List<F> list = Arrays.asList(new F(), new F());
        Iterator<Closeable> iterator = list.stream().map(f -> (Closeable) f::foo).iterator();
        while (iterator.hasNext()) {
            iterator.next().close();
        }
    }

    static class F{
        public void foo(){
            System.out.println("=foo=");
        }
    }
}
