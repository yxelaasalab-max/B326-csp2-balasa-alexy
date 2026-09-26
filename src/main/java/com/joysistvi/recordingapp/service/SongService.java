package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;

import java.util.List;

public interface SongService {

    List<Song> getAllSongs();
    Song getSongById(int id);
    List<Song> searchSong(String keyword);
    boolean createSong(Song song);
    boolean updateSong(Song song);
    boolean archiveSong(int id);
    boolean restoreSong(int id);
    boolean deleteSong(int id);
    List<Song> getAllArchivedSongs();
}
