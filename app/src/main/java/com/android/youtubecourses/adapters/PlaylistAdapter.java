package com.android.youtubecourses.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youtubecourses.models.PlaylistModel;
import com.android.youtubecourses.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private static final String TAG = "PLAYLIST_ADAPTER";
    private List<PlaylistModel> playlists = new ArrayList<>();
    private onPlayListClick onPlaylistClick;
    public PlaylistAdapter(onPlayListClick onPlaylistClick){
        this.onPlaylistClick = onPlaylistClick;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {

        holder.channelName.setText(playlists.get(position).getChannelName());
        holder.title.setText(playlists.get(position).getTitle());
        Picasso.get()
                .load(playlists.get(position).getThumbnailUrl()).into(holder.playlistImage);

    }




    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_tile,parent,false);
        return new PlaylistViewHolder(view,onPlaylistClick);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public void setPlaylists(List<PlaylistModel> playlists) {
        Log.d(TAG, "setPlaylists: "+playlists.toString());
        this.playlists = playlists;
        notifyDataSetChanged();
    }

    class PlaylistViewHolder extends  RecyclerView.ViewHolder{
        private TextView title;
        private TextView channelName;
        private ImageView playlistImage;
        onPlayListClick onPlayListClick;
        public PlaylistViewHolder(View view,onPlayListClick onPlaylistClick){
            super(view);
            title = view.findViewById(R.id.playlist_title);
            channelName = view.findViewById(R.id.playlist_channel);
            playlistImage = view.findViewById(R.id.playlist_image);
            this.onPlayListClick = onPlaylistClick;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                onPlayListClick.onPlaylistClick(getAdapterPosition());
                }
            });

        }
    }
    public interface onPlayListClick{
        void onPlaylistClick(int position);
    }
}
