package com.android.youtubecourses.models;

public class PlaylistModel {
    String thumbnailUrl;
    String title;
    String channelName;
    String playlistId;
    public PlaylistModel(String title,String thumbnailUrl,String channelName,String playlistId){
        this.channelName = channelName;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.playlistId = playlistId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getChannelName() {
        return channelName;
    }
}
