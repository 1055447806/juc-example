package com.ohh.juc.collections.custom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 CopyOnWrite 的思想实现一个 CopyOnWriteMap 集合
 * @param <K> 键
 * @param <V> 值
 */
public class CopyOnWriteMap<K, V> implements Map<K, V> {

    private volatile Map<K, V> map;

    private ReentrantLock lock = new ReentrantLock();

    public CopyOnWriteMap() {
        this.init();
    }

    private void init() {
        this.map = new HashMap<>();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            Map<K, V> newMap = new HashMap<>(this.map);
            V val = newMap.put(key, value);
            this.map = newMap;
            return val;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        try {
            lock.lock();
            Map<K, V> newMap = new HashMap<>(this.map);
            V val = newMap.remove(key);
            this.map = newMap;
            return val;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        try {
            lock.lock();
            Map<K, V> newMap = new HashMap<>(this.map);
            newMap.putAll(m);
            this.map = newMap;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        try {
            lock.lock();
            this.map = new HashMap<>();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }
}
