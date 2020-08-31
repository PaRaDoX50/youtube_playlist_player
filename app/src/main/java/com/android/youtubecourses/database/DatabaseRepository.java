package com.android.youtubecourses.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.youtubecourses.models.PlaylistModel;

import java.util.List;
public class DatabaseRepository {
    private PlaylistDao playlistDao;
    private LiveData<List<PlaylistModel>> allplaylists;
    public DatabaseRepository(Application application) {
        PlaylistDatabase database = PlaylistDatabase.getInstance(application);
        playlistDao = database.playlistDao();
        allplaylists = playlistDao.getAllPlaylists();
    }
    public void insert(PlaylistModel playlist) {
        new InsertNoteAsyncTask(playlistDao).execute(playlist);
    }
//    public void update(PlaylistModel playlist) {
//        new UpdateNoteAsyncTask(playlistDao).execute(note);
//    }
//    public void delete(PlaylistModel playlist) {
//        new DeleteNoteAsyncTask(playlistDao).execute(playlist);
//    }
//    public void deleteAllNotes() {
//        new DeleteAllNotesAsyncTask(playlistDao).execute();
//    }
    public LiveData<List<PlaylistModel>> getAllPlaylists() {
        return allplaylists;
    }
    private static class InsertNoteAsyncTask extends AsyncTask<PlaylistModel, Void, Void> {
        private PlaylistDao playlistDao;
        private InsertNoteAsyncTask(PlaylistDao playlistDao) {
            this.playlistDao = playlistDao;
        }
        @Override
        protected Void doInBackground(PlaylistModel... playlists) {
            playlistDao.insert(playlists[0]);
            Log.d("insert function", "doInBackground: "+playlists[0].toString());
            return null;
        }
    }
//    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
//        private NoteDao noteDao;
//        private UpdateNoteAsyncTask(NoteDao noteDao) {
//            this.noteDao = noteDao;
//        }
//        @Override
//        protected Void doInBackground(Note... notes) {
//            noteDao.update(notes[0]);
//            return null;
//        }
//    }
//    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
//        private NoteDao noteDao;
//        private DeleteNoteAsyncTask(NoteDao noteDao) {
//            this.noteDao = noteDao;
//        }
//        @Override
//        protected Void doInBackground(Note... notes) {
//            noteDao.delete(notes[0]);
//            return null;
//        }
//    }
//    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
//        private NoteDao noteDao;
//        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
//            this.noteDao = noteDao;
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            noteDao.deleteAllNotes();
//            return null;
//        }
//    }
}
