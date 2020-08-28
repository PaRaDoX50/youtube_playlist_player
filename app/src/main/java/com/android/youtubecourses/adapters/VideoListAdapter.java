package com.android.youtubecourses.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youtubecourses.R;
import com.android.youtubecourses.models.VideoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {
    private static final String TAG = "VIDEO_LIST_ADAPTER";
    List<VideoModel> videos = new ArrayList<>();
    OnVideoClicked onVideoClicked;

    public VideoListAdapter(OnVideoClicked onVideoClicked){
        this.onVideoClicked = onVideoClicked;
    }
    @Override
    public int getItemCount() {
        return videos.size();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_tile,parent,false);
        return new VideoViewHolder(view,onVideoClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.channelName.setText(videos.get(position).getChannelTitle());
        holder.title.setText(videos.get(position).getTitle());
        Picasso.get().load(videos.get(position).getThumbnailUrl()).into(holder.videoImage);
    }

    public void setVideos(List<VideoModel> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    class VideoViewHolder extends  RecyclerView.ViewHolder{
        private TextView title;
        private TextView channelName;
        private ImageView videoImage;
        OnVideoClicked onVideoClicked;
        public VideoViewHolder(View view,  OnVideoClicked onVideoClick){
            super(view);
            title = view.findViewById(R.id.video_title);
            channelName = view.findViewById(R.id.video_channel);
           videoImage = view.findViewById(R.id.video_image);
           this.onVideoClicked = onVideoClick;
           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onVideoClicked.onVideoClick(getAdapterPosition());
               }
           });

        }
    }
    public interface OnVideoClicked{
        void onVideoClick(int position);

    }
}
