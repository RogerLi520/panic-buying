package com.wenyanwen123.buy.commons.config;

import net.sf.ehcache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThirdBeanConfig {

    @Bean
    CacheManager getCacheManager(){
        CacheManager cacheManager = new CacheManager();
        return cacheManager;
    }

}
