package com.monitor.common.service.impl;

import com.google.common.cache.Cache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.monitor.common.module.CacheConfig;
import com.monitor.common.service.LocalCacheService;
import com.monitor.common.service.RealDataFetcher;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 11:00
 * @Description:
 */
public abstract class LocalCacheServiceImpl<K, V> implements LocalCacheService<K,V> {

    protected Cache<K, V> cache;
//    protected RealDataFetcher<K, V> fetcher;

    LocalCacheServiceImpl(CacheConfig config) {
//        fetcher = new RealDataFetcher<K, V>() {
//            @Override
//            public V get(K key) {
//                return getRealData(key);
//            }
//
//            @Override
//            public Map<K, V> getAll(Collection<K> keys) {
//                return getRealData(keys);
//            }
//        };
    }

    @Override
    public long getCacheItemSize() {
        return cache.size();
    }

    @Override
    public Map<K, V> mget(List<K> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Maps.newHashMap();
        }
        Map<K, V> cacheResult = cache.getAllPresent(keys);
//        if (CollectionUtils.isEmpty((Collection<?>) cacheResult)) {
//            cacheResult = fetcher.getAll(keys);
//        }
//        if (CollectionUtils.isNotEmpty((Collection<?>) cacheResult)) {
//            set(cacheResult);
//        }
        return cacheResult;
    }

    @Override
    public V mget(K key) {
        V value = cache.getIfPresent(key);
//        if (null == value) {
//            value = fetcher.get(key);
//        }
//        if (null != value) {
//            set(key,value);
//        }
        return value;
    }

    @Override
    public void set(Map<K, V> map) {
        cache.putAll(map);
    }

    @Override
    public void set(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void destory() {
        cache.cleanUp();
    }

    @Override
    public void invalidate(K key) {
        cache.invalidate(key);
    }

    //    public abstract V getRealData(K key);
//
//    public abstract Map<K, V> getRealData(Collection<K> keys);
}
