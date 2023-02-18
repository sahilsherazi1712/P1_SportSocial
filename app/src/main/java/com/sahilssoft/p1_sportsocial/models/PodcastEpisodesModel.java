package com.sahilssoft.p1_sportsocial.models;

public class PodcastEpisodesModel {
    String timeEpisode, descEpisode, durationEpisode;
    int moreEpisodes, image;

    public PodcastEpisodesModel(String timeEpisode, String descEpisode, String durationEpisode, int moreEpisodes) {
        this.timeEpisode = timeEpisode;
        this.descEpisode = descEpisode;
        this.durationEpisode = durationEpisode;
        this.moreEpisodes = moreEpisodes;
    }

    public PodcastEpisodesModel(String descEpisode, String durationEpisode, int image) {
        this.descEpisode = descEpisode;
        this.durationEpisode = durationEpisode;
        this.image = image;
    }

    public String getTimeEpisode() {
        return timeEpisode;
    }

    public void setTimeEpisode(String timeEpisode) {
        this.timeEpisode = timeEpisode;
    }

    public String getDescEpisode() {
        return descEpisode;
    }

    public void setDescEpisode(String descEpisode) {
        this.descEpisode = descEpisode;
    }

    public String getDurationEpisode() {
        return durationEpisode;
    }

    public void setDurationEpisode(String durationEpisode) {
        this.durationEpisode = durationEpisode;
    }

    public int getMoreEpisodes() {
        return moreEpisodes;
    }

    public void setMoreEpisodes(int moreEpisodes) {
        this.moreEpisodes = moreEpisodes;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
