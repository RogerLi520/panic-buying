package com.wenyanwen123.buy.common.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Desc ehcache缓存
 * @Author liww
 * @Date 2019/9/26
 * @Version 1.0
 */
@Component
public class CacheUtil {

    @Autowired
    private CacheManager cacheManager;

    private CacheUtil() {
    }

    /**
     * @Desc 设置内容
     * @Author liww
     * @Date 2019/9/26
     * @Param [cacheName, key, value]
     * @return void
     */
    public void put(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * @Desc 获取内容
     * @Author liww
     * @Date 2019/9/26
     * @Param [cacheName, key]
     * @return T
     */
    public <T> T get(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = cache.get(key);
        return element == null ? null : (T) element.getObjectValue();
    }

    /**
     * @Desc 获取值，如果值不存在则存入值
     * @Author liww
     * @Date 2019/9/26
     * @Param [cacheName, key, onCacheEmpty]
     * @return T
     */
    public <T> T get(String cacheName, String key, OnCacheEmpty onCacheEmpty) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = cache.get(key);
        if (element == null) {
            element = new Element(key, onCacheEmpty.getCacheData());
            cache.put(element);
        }
        return (T) element.getObjectValue();
    }

    /**
     * @Desc 删除值
     * @Author liww
     * @Date 2019/9/26
     * @Param [cacheName, key]
     * @return void
     */
    public void remove(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        cache.remove(key);
    }

    /**
     * @Desc 获取cache对象
     * @Author liww
     * @Date 2019/9/26
     * @Param [cacheName]
     * @return net.sf.ehcache.Cache
     */
    public Cache get(String cacheName) {
        return cacheManager.getCache(cacheName);
    }

    public interface OnCacheEmpty {
        Object getCacheData();
    }

}
