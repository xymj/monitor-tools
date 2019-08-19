package com.monitor.common.service;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 16:24
 * @Description:
 */
public interface RealDataFetcher<K, V> {

    public V get(K key);

    public Map<K, V> getAll(Collection<K> keys);
}
