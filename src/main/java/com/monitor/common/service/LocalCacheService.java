package com.monitor.common.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 10:48
 * @Description:
 */
public interface LocalCacheService<K, V> {

    public long getCacheItemSize();

    public Map<K, V> mget(List<K> keys);

    public V mget(K key);

    public void set(Map<K,V> map);

    public void set(K key, V value);

    public void destory();

    /** Discards any cached value for key {@code key}. */
    void invalidate(K key);
}
