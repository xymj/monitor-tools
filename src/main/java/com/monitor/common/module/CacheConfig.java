package com.monitor.common.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 11:10
 * @Description:
 */

@Data
@AllArgsConstructor
@ToString
public class CacheConfig {
    private String cacheName;
    private String cacheServiceName;
    private int cacheSize;
    private long expire;
}
