package com.monitor.common.service.impl;

import com.monitor.common.module.CacheConfig;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 11:31
 * @Description:
 */
public class MapCacheServiceImpl<K, V> extends LocalCacheServiceImpl<K, V> {

    MapCacheServiceImpl(CacheConfig config) {
        super(config);
        cache = new MapCacheAdapter(config);
    }

//    @Override
//    public V getRealData(K key) {
//        return null;
//    }
//
//    @Override
//    public Map<K, V> getRealData(Collection<K> keys) {
//        return null;
//    }

}
