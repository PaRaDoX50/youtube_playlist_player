package com.android.youtubecourses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.youtubecourses.R;
import com.android.youtubecourses.adapters.VideoListAdapter;
import com.android.youtubecourses.models.VideoModel;
import com.android.youtubecourses.viewmodel.YoutubeViewModel;

import java.util.List;

public class VideoListActivity extends AppCompatActivity implements VideoListAdapter.OnVideoClicked {
    private YoutubeViewModel youtubeViewModel;
    String playlistId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        getSupportActionBar().setTitle("Course: " + getIntent().getStringExtra("playlistName"));

        final VideoListAdapter videoListAdapter = new VideoListAdapter(this);
        playlistId = getIntent().getStringExtra("playlistId");


        youtubeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(YoutubeViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.video_recyclerview);
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


    }

    @Override
    public void onVideoClick(int position) {
        Intent route = new Intent(this, YoutubePlayerActivity.class);
        route.putExtra("playlistId", playlistId);
        route.putExtra("position", position);
        startActivity(route);
    }
}
