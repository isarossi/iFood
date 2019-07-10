package com.recommendation.cache.musicPlaylist.model;

import java.io.Serializable;
import java.util.List;

public class GenrePlaylist implements Serializable {
    private String genre;
    private List<String> playlist;

    public GenrePlaylist(String genre, List<String> playlist) {
        this.genre = genre;
        this.playlist = playlist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }
}
