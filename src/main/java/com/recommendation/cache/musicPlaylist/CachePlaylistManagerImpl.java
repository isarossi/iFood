package com.recommendation.cache.musicPlaylist;

import com.recommendation.cache.musicPlaylist.model.GenrePlaylist;
import com.recommendation.cache.weatherforecast.model.Weather;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class CachePlaylistManagerImpl implements CachePlaylistManager{
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
    }

    @Override
    public Object get(String key) {
        return null;
    }
}
