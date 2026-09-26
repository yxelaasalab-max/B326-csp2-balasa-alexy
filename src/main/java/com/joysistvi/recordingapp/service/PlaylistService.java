package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;

public interface PlaylistService {

    List<Playlist> getAllPlaylists();
    Playlist getPlaylistById(int id);
    List<Playlist> searchPlaylist(String keyword);
    boolean createPlaylist(Playlist playlist);
    boolean updatePlaylist(Playlist playlist);
    boolean archivePlaylist(int id);
    boolean restorePlaylist(int id);
    boolean deletePlaylist(int id);
    List<Playlist> getAllArchivedPlaylists();
    // Playlist Songs
    boolean addSongToPlaylist(int playlistId, int songId);
    boolean removeSongFromPlaylist(int playlistId, int songId);
    List<Song> getSongsFromPlaylist(int playlistId);
}
