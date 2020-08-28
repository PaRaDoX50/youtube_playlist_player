package com.android.youtubecourses.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youtubecourses.adapters.PlaylistAdapter;
import com.android.youtubecourses.models.PlaylistModel;
import com.android.youtubecourses.R;
import com.android.youtubecourses.viewmodel.YoutubeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlaylistAdapter.onPlayListClick {

    private YoutubeViewModel youtubeViewModel;
    List<PlaylistModel> playlists = new ArrayList<>();
    ProgressBar progressBar;
    FrameLayout noContent;

    @Override
    public void onPlaylistClick(int position) {
        Intent route = new Intent(this, VideoListActivity.class);
        route.putExtra("playlistId", playlists.get(position).getPlaylistId());
        route.putExtra("playlistName", playlists.get(position).getTitle());

        startActivity(route);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PlaylistAdapter playlistAdapter = new PlaylistAdapter(this);
        progressBar = findViewById(R.id.loading);
        noContent = findViewById(R.id.no_content);


        youtubeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(YoutubeViewModel.class);
        final RecyclerView recyclerView = findViewById(R.id.playlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(playlistAdapter);
        recyclerView.setHasFixedSize(true);

        youtubeViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        youtubeViewModel.getSearchResult().observe(this, new Observer<List<PlaylistModel>>() {
            @Override
            public void onChanged(List<PlaylistModel> playlistModels) {
                if(playlistModels.isEmpty()){
                    noContent.setVisibility(View.VISIBLE);
                }
                else{
                    noContent.setVisibility(View.GONE);
                }
                playlists = playlistModels;
                playlistAdapter.setPlaylists(playlistModels);
            }
        });
        final EditText editText = findViewById(R.id.search);
        Button button = findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeViewModel.search(editText.getText().toString());
            }
        });


    }
}