
package com.recommendation.cache.musicPlaylist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Track implements Serializable {
    private Album album;
    private List<Artist> artists = null;
    private String name;

    public Track(Album album, List<Artist> artists, String name) {
        this.album = album;
        this.artists = artists;
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
