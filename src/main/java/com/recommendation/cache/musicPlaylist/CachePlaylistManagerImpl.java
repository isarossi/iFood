package com.recommendation.cache.musicPlaylist;

import com.recommendation.cache.musicPlaylist.model.GenrePlaylist;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;
@Repository
public class CachePlaylistManagerImpl implements CachePlaylistManager {
    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;
    private ValueOperations valueOperations;

    public CachePlaylistManagerImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void save(GenrePlaylist genrePlaylist) {
        valueOperations.set(genrePlaylist.getGenre(), genrePlaylist);
        setExpireTime(genrePlaylist.getGenre(), 1, TimeUnit.DAYS);
    }

    public void setExpireTime(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    @Override
    public Object get(String key) {
        return valueOperations.get(key);
    }
}
