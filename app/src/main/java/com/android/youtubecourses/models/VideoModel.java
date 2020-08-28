package com.android.youtubecourses.models;

public class VideoModel {
    String videoId;
    String thumbnailUrl;
    String channelTitle;
    String title;
    String playlistId;

    public VideoModel(String videoId,
               String thumbnailUrl,
               String channelTitle,
               String title, String playlistId) {
        this.channelTitle = channelTitle;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.videoId = videoId;
        this.playlistId = playlistId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getTitle() {
        return title;
    }
}
