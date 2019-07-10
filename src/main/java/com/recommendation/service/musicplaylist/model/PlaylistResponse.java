package com.recommendation.service.musicplaylist.model;

import java.util.List;

public class PlaylistResponse {
    private List<String> playlist;

    public PlaylistResponse(List<String> playlist) {
        this.playlist = playlist;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }
}
