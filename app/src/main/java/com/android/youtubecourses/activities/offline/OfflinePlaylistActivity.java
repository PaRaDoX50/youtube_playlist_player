package com.android.youtubecourses.activities.offline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.youtubecourses.R;
import com.android.youtubecourses.activities.MainActivity;
import com.android.youtubecourses.activities.VideoListActivity;
import com.android.youtubecourses.adapters.PlaylistAdapter;
import com.android.youtubecourses.models.PlaylistModel;
import com.android.youtubecourses.viewmodel.DatabaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlaylistActivity extends AppCompatActivity implements PlaylistAdapter.onPlayListClick {
    @Override
    public void onPlaylistClick(int position) {

    }
    private FrameLayout noContent;
    List<PlaylistModel> playlists = new ArrayList<>();

    private DatabaseViewModel databaseViewModel;
    private Button onlineModeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_playlist);

        final PlaylistAdapter playlistAdapter = new PlaylistAdapter(this);
        noContent = findViewById(R.id.no_content_offline);
        onlineModeButton = findViewById(R.id.online_mode);

        onlineModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    Intent route = new Intent(OfflinePlaylistActivity.this, MainActivity.class);


                    startActivity(route);
                    finish();
                }
                else{
                    Toast.makeText(OfflinePlaylistActivity.this,"No Internet Connection. Can't Switch to online mode",Toast.LENGTH_SHORT).show();
                }
            }
        });
        databaseViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(DatabaseViewModel.class);
        final RecyclerView recyclerView = findViewById(R.id.playlist_recyclerview_offline);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(playlistAdapter);
        recyclerView.setHasFixedSize(true);
        databaseViewModel.getAllPlaylists().observe(this, new Observer<List<PlaylistModel>>() {
            @Override
            public void onChanged(List<PlaylistModel> playlistModels) {
                if(playlistModels.isEmpty()){
                    noContent.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else {
                    noContent.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    playlists = playlistModels;
                    playlistAdapter.setPlaylists(playlistModels);
                }
                }
        });

    }
}
