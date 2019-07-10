package com.recommendation.cache.musicPlaylist;

import com.recommendation.cache.musicPlaylist.model.GenrePlaylist;

public interface CachePlaylistManager {
    void save(GenrePlaylist genrePlaylist);
    Object get(String key);
}
