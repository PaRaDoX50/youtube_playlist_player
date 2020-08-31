package com.android.youtubecourses.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.youtubecourses.database.DatabaseRepository;
import com.android.youtubecourses.models.PlaylistModel;

import java.util.List;
public class DatabaseViewModel extends AndroidViewModel {
    private DatabaseRepository repository;
    private LiveData<List<PlaylistModel>> allPlaylists;
    public DatabaseViewModel(@NonNull Application application) {
        super(application);
        repository = new DatabaseRepository(application);
        allPlaylists = repository.getAllPlaylists();
    }
    public void insert(PlaylistModel playlist) {
        repository.insert(playlist);
    }
//    public void update(PlaylistModel playlist) {
//        repository.update(note);
//    }
//    public void delete(Note note) {
//        repository.delete(note);
//    }
//    public void deleteAllNotes() {
//        repository.deleteAllNotes();
//    }
    public LiveData<List<PlaylistModel>> getAllPlaylists() {
        return allPlaylists;
    }
}