package com.monitor.common.service.impl;

import com.monitor.common.module.CacheConfig;
import com.monitor.common.service.LocalCacheService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 11:27
 * @Description:
 */
public class SwitchCacheServiceImpl<K, V> implements LocalCacheService<K, V> {

    private LocalCacheService localCacheService;

    private CacheConfig cacheConfig;

    public SwitchCacheServiceImpl(CacheConfig config) {
        cacheConfig = config;
        String serviceName = config.getCacheServiceName();
        if (StringUtils.isEmpty(serviceName)) {
            localCacheService = new MapCacheServiceImpl(config);
        }
        switch (serviceName) {
            case "guava":
                localCacheService = null;
                break;
            case "caffeine":
                localCacheService = null;
                break;
            default:
                    localCacheService = new MapCacheServiceImpl(config);
        }

    }


    @Override
    public long getCacheItemSize() {
        return localCacheService.getCacheItemSize();
    }

    @Override
    public Map<K, V> mget(List<K> keys) {
        return localCacheService.mget(keys);
    }

    @Override
    public V mget(K key) {
        return (V) localCacheService.mget(key);
    }

    @Override
    public void set(Map<K, V> map) {
        localCacheService.set(map);
    }

    @Override
    public void set(K key, V value) {
        localCacheService.set(key,value);
    }

    @Override
    public void destory() {
        localCacheService.destory();
    }

    @Override
    public void invalidate(K key) {
        localCacheService.invalidate(key);
    }

    @Override
    public String toString() {
        return "SwitchCacheServiceImpl{" +
                "localCacheService=" + localCacheService +
                ", cacheConfig=" + cacheConfig +
                '}';
    }
}
