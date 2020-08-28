package com.android.youtubecourses.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.android.youtubecourses.models.PlaylistModel;
import com.android.youtubecourses.models.VideoModel;
import com.android.youtubecourses.viewmodel.PlaylistRepository;

import java.util.List;

public class YoutubeViewModel extends AndroidViewModel {
    private PlaylistRepository repository;
    private MutableLiveData<List<PlaylistModel>> searchResult;
    private MutableLiveData<List<VideoModel>> playlistVideos;
    public YoutubeViewModel(Application application) {
        super(application);
        repository = new PlaylistRepository();
        searchResult = repository.getSearchResult();
        playlistVideos = repository.getPlaylistVideos();
    }
    public MutableLiveData<Boolean> getLoading() {
        return repository.getIsLoading();
    }

    public MutableLiveData<List<PlaylistModel>> getSearchResult() {
        return searchResult;
    }
    public MutableLiveData<List<VideoModel>> getPlaylistVideos() {
        return playlistVideos;
    }
    public void search(String query) {
        repository.search(query);
    }
    public void setPlaylistId(String playlistId) {
        repository.setPlaylistId(playlistId);
    }


}