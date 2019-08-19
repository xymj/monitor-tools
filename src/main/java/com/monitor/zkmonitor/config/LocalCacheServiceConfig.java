package com.monitor.zkmonitor.config;

import com.monitor.common.module.CacheConfig;
import com.monitor.common.service.LocalCacheService;
import com.monitor.common.service.impl.SwitchCacheServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 15:28
 * @Description:
 */

@Configuration
//不会使用，需要进一步研究  @ConfigurationProperties(prefix = "cache")//需添加spring-boot-configuration-processor  maven依赖
@PropertySource("classpath:cache/cache.properties")
//@Data
public class LocalCacheServiceConfig {

    @Value("${cache.cache_name}")
    private String cacheName;
    @Value("${cache.cache_service_name}")
    private String cacheServiceName;
    @Value("${cache.cache_size}")
    private int cacheSize;
    @Value("${cache.cache_expire}")
    private long expire;



    @Bean
    public LocalCacheService getLocalCacheService() {
        return new SwitchCacheServiceImpl(
                new CacheConfig(cacheName,cacheServiceName,cacheSize,expire));
    }
}
