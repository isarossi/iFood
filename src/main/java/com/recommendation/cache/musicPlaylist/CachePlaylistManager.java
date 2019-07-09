package com.recommendation.cache.musicPlaylist;

import com.recommendation.cache.musicPlaylist.model.GenrePlaylist;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;

public interface CachePlaylistManager {
    void save(GenrePlaylist genrePlaylist);
    Object get(String key);
}
