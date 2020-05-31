package com.sudria.demo.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
public class CachingConfig {

  @Autowired
  CacheManager cacheManager;

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager("classes");
  }

  public void evictAllCaches() {
    cacheManager.getCacheNames().stream()
        .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
  }

  @Scheduled(fixedRate = 5000)
  public void evictAllcachesAtIntervals() {
    evictAllCaches();
  }

}