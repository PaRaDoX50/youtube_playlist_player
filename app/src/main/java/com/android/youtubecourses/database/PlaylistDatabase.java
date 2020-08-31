package com.android.youtubecourses.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.youtubecourses.models.PlaylistModel;

@Database(entities = {PlaylistModel.class}, version = 2)
public abstract class PlaylistDatabase extends RoomDatabase {
    private static PlaylistDatabase instance;
    public abstract PlaylistDao playlistDao();
    public static synchronized PlaylistDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PlaylistDatabase.class, "playlist_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}