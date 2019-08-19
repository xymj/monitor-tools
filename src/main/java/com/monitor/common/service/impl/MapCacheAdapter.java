package com.monitor.common.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import com.monitor.common.module.CacheConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 11:33
 * @Description:
 */

@Slf4j
public class MapCacheAdapter<K, V> implements Cache<K, V> {

    private Map<K, V> cache;
    public MapCacheAdapter(CacheConfig config) {
        cache = new ConcurrentHashMap<K, V>(config.getCacheSize());
    }

    @Override
    public V getIfPresent(Object key) {
        return cache.get(key);
    }

    @Override
    public V get(K key, Callable<? extends V> loader) throws ExecutionException {
        log.info("todo");
        return null;
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
        synchronized (cache) {
            Map<K, V> res = new HashMap<>();
            keys.forEach(key -> {
                if (null == key) {
                    return;
                }
                res.put((K) key, cache.get(key));
            });

            return (ImmutableMap<K, V>) res;
        }
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        cache.putAll(m);
    }

    @Override
    public void invalidate(Object key) {
        cache.remove(key);
    }

    @Override
    public void invalidateAll(Iterable<?> keys) {
    }

    @Override
    public void invalidateAll() {
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public CacheStats stats() {
        return null;
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return (ConcurrentMap<K, V>) cache;
    }

    @Override
    public void cleanUp() {
        cache.clear();
    }
}
