package com.kankan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Component
public class CacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void cacheActiveCode(String key, String activeCode) {
        redisTemplate.opsForValue().set(key, activeCode);
    }


    public String getActiveCode(String key) {
        return  redisTemplate.opsForValue().get(key);
    }
}
