package com.android.youtubecourses.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.youtubecourses.models.PlaylistModel;

import java.util.List;

@Dao
public interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlaylistModel playlist);
//    @Update
//    void update(PlaylistModel playlist);
//    @Delete
//    void delete(PlaylistModel playlist);
//    @Query("DELETE FROM playlist_table")
//    void deleteAllNotes();
    @Query("SELECT * FROM playlist_table")
    LiveData<List<PlaylistModel>> getAllPlaylists();
}
