package com.recommendation.cache.musicPlaylist;

import com.recommendation.cache.musicPlaylist.model.cacheGenrePlaylist;

public interface CachePlaylistManager {
    void save(cacheGenrePlaylist genrePlaylist);
    Object get(String key);
}
