package com.android.youtubecourses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.youtubecourses.R;
import com.android.youtubecourses.api.YoutubeApi;
import com.android.youtubecourses.adapters.VideoListAdapter;
import com.android.youtubecourses.models.VideoModel;
import com.android.youtubecourses.viewmodel.YoutubeViewModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

import java.util.List;

public class YoutubePlayerActivity extends AppCompatActivity implements VideoListAdapter.OnVideoClicked {
//    YouTubePlayerView youTubePlayerView;
private YoutubeViewModel youtubeViewModel;

    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer player;
     String playlistId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        playlistId = getIntent().getStringExtra("playlistId");
        final VideoListAdapter videoListAdapter = new VideoListAdapter(this);

        youtubeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(YoutubeViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.youtube_player_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(videoListAdapter);
        recyclerView.setHasFixedSize(true);

        youtubeViewModel.getPlaylistVideos().observe(this, new Observer<List<VideoModel>>() {
            @Override
            public void onChanged(List<VideoModel> videoModels) {
                videoListAdapter.setVideos(videoModels);
            }
        });
        youtubeViewModel.setPlaylistId(playlistId);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;
                youTubePlayer.loadPlaylist(playlistId,getIntent().getIntExtra("position",0),0);


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubePlayerActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        };
        YouTubePlayerSupportFragmentX frag =
                (YouTubePlayerSupportFragmentX) getSupportFragmentManager().findFragmentById(R.id.youtube_player);
        frag.initialize(YoutubeApi.API_KEY, onInitializedListener);
//        youTubePlayerView = findViewById(R.id.youtube_player);


    }

    @Override
    public void onVideoClick(int position) {
     if(player != null){
         player.loadPlaylist(playlistId,position,0);
     }
    }
}
