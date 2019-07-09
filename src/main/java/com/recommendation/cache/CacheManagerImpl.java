package com.recommendation.cache;

import org.springframework.beans.factory.annotation.Autowired;

public class CacheManagerImpl implements CacheManager {

    private RedisUtil<Object> redisUtil;

    @Autowired
    public CacheManagerImpl(RedisUtil<Object> redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void save(Object o) {
        redisUtil.putMap("Cache", o.hashCode(), o);
        //redisUtil.setExpire(TABLE_STUDENT,1,TimeUnit.DAYS);
    }
}
