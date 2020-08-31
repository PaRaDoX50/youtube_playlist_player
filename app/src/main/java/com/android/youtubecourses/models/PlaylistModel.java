package com.android.youtubecourses.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist_table", indices = @Index(value = {"thumbnailUrl"}, unique = true))
public class PlaylistModel {
//    indices = {@Index(value = {"playlistId"},
//            unique = true)}
    private String thumbnailUrl;
    private String title;
    private String channelName;

    @PrimaryKey @NonNull
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
