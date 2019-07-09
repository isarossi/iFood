package com.recommendation.cache.musicPlaylist.model;

import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;

import java.io.Serializable;

public class GenrePlaylist implements Serializable {
    private String genre;
    private PlaylistJsonResponse playlistResponse;

    public GenrePlaylist(String genre, PlaylistJsonResponse playlistResponse) {
        this.genre = genre;
        this.playlistResponse = playlistResponse;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public PlaylistJsonResponse getPlaylistResponse() {
        return playlistResponse;
    }

    public void setPlaylistResponse(PlaylistJsonResponse playlistResponse) {
        this.playlistResponse = playlistResponse;
    }
}
