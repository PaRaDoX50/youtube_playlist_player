package com.android.youtubecourses.viewmodel;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;


import com.android.youtubecourses.api.YoutubeApi;
import com.android.youtubecourses.models.PlaylistModel;
import com.android.youtubecourses.models.VideoModel;

import java.util.List;


public class PlaylistRepository {
    private YoutubeApi youtubeApi = YoutubeApi.getInstance();
    private MutableLiveData<List<PlaylistModel>> searchResultMutableLiveData;
    private MutableLiveData<List<VideoModel>> playlistVideos;
    private MutableLiveData<Boolean> isLoading;

    public void search(String query){
        new GetPlaylistDataAsyncTask(youtubeApi){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isLoading.setValue(true);
            }

            @Override
            protected void onPostExecute(List<PlaylistModel> searchResult) {
                super.onPostExecute(searchResult);
                //Here we get the playlist data
                isLoading.setValue(false);
                searchResultMutableLiveData.setValue(searchResult);
            }}.execute(query);
    }
    public void setPlaylistId(String playlistId){
        new GetPlaylistVideosAsyncTask(youtubeApi){
            @Override
            protected void onPostExecute(List<VideoModel> videoModels) {
                super.onPostExecute(videoModels);
                playlistVideos.setValue(videoModels);
            }
        }.execute(playlistId);
    }
    public MutableLiveData<List<VideoModel>> getPlaylistVideos(){
        if(playlistVideos == null){
            playlistVideos = new MutableLiveData<>();
        }


        return playlistVideos;
    }
    public MutableLiveData<Boolean> getIsLoading(){
        if(isLoading == null){
            isLoading = new MutableLiveData<>();
        }


        return isLoading;
    }

    public MutableLiveData<List<PlaylistModel>> getSearchResult(){
        if(searchResultMutableLiveData == null){
            searchResultMutableLiveData = new MutableLiveData<>();
        }


        return searchResultMutableLiveData;
    }
    private static class GetPlaylistDataAsyncTask extends AsyncTask<String, Void, List<PlaylistModel>> {


        private YoutubeApi mYoutubeApi;
        GetPlaylistDataAsyncTask(YoutubeApi api){
            mYoutubeApi = api;
        }



        @Override
        protected List<PlaylistModel> doInBackground(String... params) {

            final String query = params[0];
            List<PlaylistModel> searchResult;


            searchResult = mYoutubeApi.getSearchResult(query);


            return searchResult;
        }
    }

    private static class GetPlaylistVideosAsyncTask extends AsyncTask<String, Void, List<VideoModel>> {


        private YoutubeApi mYoutubeApi;
        GetPlaylistVideosAsyncTask(YoutubeApi api){
            mYoutubeApi = api;
        }



        @Override
        protected List<VideoModel> doInBackground(String... params) {

            final String playlistId = params[0];
            List<VideoModel> videos;


            videos = mYoutubeApi.getPlaylistVideos(playlistId);


            return videos;
        }
    }



}
