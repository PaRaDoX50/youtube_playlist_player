package com.android.youtubecourses.api;

import android.util.Log;

import com.android.youtubecourses.models.PlaylistModel;
import com.android.youtubecourses.models.VideoModel;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.SearchListResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeApi {

    public static final String API_KEY = "AIzaSyD5SSBZJVqwv9xfWf8CgRUlbyQaXbZv_o0";

    private static final String TAG = "YOUTUBE_API";
    private HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    private JsonFactory mJsonFactory = JacksonFactory.getDefaultInstance();

    private YouTube mYouTubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
            .setApplicationName("com.android.youtubecourses")
            .build();
    ;
    private static YoutubeApi instance;

    public static synchronized YoutubeApi getInstance() {
        if (instance == null) {
            instance = new YoutubeApi();
        }
        return instance;
    }

    public List<PlaylistModel> getSearchResult(String query) {
        SearchListResponse searchListResponse;
        String jsonData = "{}";
        List<PlaylistModel> searchData = new ArrayList<>();
        try {
             final String YOUTUBE_PLAYLIST_FIELDS = "items(id,snippet(title,channelTitle,thumbnails(medium)))";
            searchListResponse = mYouTubeDataApi.search().list("snippet").setQ(query).setType("playlist")
                    .setFields(YOUTUBE_PLAYLIST_FIELDS).setMaxResults(10L)
                    .setKey(API_KEY).execute();
            jsonData = searchListResponse.toPrettyString();

        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            Log.d(TAG, "getSearchResult: "+jsonData);
            JSONObject jObj = new JSONObject(jsonData);
            JSONArray jsonArray = jObj.getJSONArray("items");
            for(int i = 0; i< jsonArray.length();i++){
                JSONObject playlistObj = jsonArray.getJSONObject(i);
                JSONObject idObj = playlistObj.getJSONObject("id");
                String playlistId = idObj.getString("playlistId");
                JSONObject snippet = playlistObj.getJSONObject("snippet");
                String playlistTitle = snippet.getString("title");
                String channelTitle = snippet.getString("channelTitle");
                String imageUrl = "xyz";
                if(snippet.has("thumbnails")){
                    JSONObject thumbnails= snippet.getJSONObject("thumbnails");
                    if(thumbnails.has("medium")) {
                        JSONObject mediumThumbnail = thumbnails.getJSONObject("medium");
                        imageUrl = mediumThumbnail.getString("url");
                    }
                }
                else{
                    imageUrl = "xyz";
                }

                searchData.add(new PlaylistModel(playlistTitle,imageUrl,channelTitle,playlistId));


            }
            return searchData;
        }
        catch (JSONException e) {
            Log.d(TAG, "getSearchResult: "+e.toString());
            return null;
        }
    }

    public List<VideoModel> getPlaylistVideos(String playlistId) {
        PlaylistItemListResponse playlistItemListResponse;
        String jsonData = "{}";
        List<VideoModel> videos = new ArrayList<>();
        try {
            int ten = 10;
            final String YOUTUBE_PLAYLIST_FIELDS = "items(id,snippet(title,channelTitle,thumbnails(medium)))";
           playlistItemListResponse = mYouTubeDataApi.playlistItems().list("snippet").setPlaylistId(playlistId).setMaxResults(10L).setKey(API_KEY).execute();

            jsonData = playlistItemListResponse.toPrettyString();

        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            Log.d(TAG, "getSearchResult: "+jsonData);
            JSONObject jObj = new JSONObject(jsonData);
            JSONArray jsonArray = jObj.getJSONArray("items");
            for(int i = 0; i< jsonArray.length();i++){
                JSONObject videoObj = jsonArray.getJSONObject(i);
//                JSONObject idObj = playlistObj.getJSONObject("id");
//                String playlistId = idObj.getString("playlistId");
                JSONObject snippet = videoObj.getJSONObject("snippet");
                String videoTitle = snippet.getString("title");
                String channelTitle = snippet.getString("channelTitle");
                String imageUrl = "xyz";
                if(snippet.has("thumbnails")){
                    JSONObject thumbnails= snippet.getJSONObject("thumbnails");
                    if(thumbnails.has("medium")) {
                        JSONObject mediumThumbnail = thumbnails.getJSONObject("medium");
                        imageUrl = mediumThumbnail.getString("url");
                    }
                }
                else{
                    imageUrl = "xyz";
                }



                JSONObject videoInfo = snippet.getJSONObject("resourceId");
                String videoId = videoInfo.getString("videoId");


                videos.add(new VideoModel(videoId,imageUrl,channelTitle,videoTitle,playlistId));




            }
            return videos;
        }
        catch (JSONException e) {
            Log.d(TAG, "getSearchResult: "+e.toString());
            return null;
        }
    }

}
