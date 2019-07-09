package com.recommendation.cache.musicPlaylist.model;

import java.io.Serializable;
import java.util.List;

public class cacheGenrePlaylist implements Serializable {
    private String genre;
    private List<Track> playlist;

    public cacheGenrePlaylist(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Track> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Track> playlist) {
        this.playlist = playlist;
    }
}
