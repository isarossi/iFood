
package com.recommendation.service.musicplaylist.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaylistResponse {

    @SerializedName("tracks")
    @Expose
    private List<Track> tracks = null;
    @SerializedName("seeds")
    @Expose
    private List<Seed> seeds = null;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<Seed> seeds) {
        this.seeds = seeds;
    }

}
