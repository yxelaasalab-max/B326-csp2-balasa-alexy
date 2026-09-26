package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.service.PlaylistService;

import java.util.List;

public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    // READ
    public List<Playlist> handleViewAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    public Playlist handleGetPlaylistById(int id) {
        return playlistService.getPlaylistById(id);
    }

    public List<Playlist> handleSearchPlaylist(String keyword) {
        return playlistService.searchPlaylist(keyword);
    }

    // CREATE
    public boolean handleCreatePlaylist(Playlist playlist) {
        return playlistService.createPlaylist(playlist);
    }

    // UPDATE
    public boolean handleUpdatePlaylist(Playlist playlist) {
        return playlistService.updatePlaylist(playlist);
    }

    // ARCHIVE AND RESTORE
    public boolean handleArchivePlaylist(int id) {
        return playlistService.archivePlaylist(id);
    }

    public boolean handleRestorePlaylist(int id) {
        return playlistService.restorePlaylist(id);
    }

    // DELETE
    public boolean handleDeletePlaylist(int id) {
        return playlistService.deletePlaylist(id);
    }

    public List<Playlist> handleGetAllArchivedPlaylists() {
        return playlistService.getAllArchivedPlaylists();
    }

    // =========================
    // PLAYLIST SONGS
    // =========================

    public boolean handleAddSongToPlaylist(int playlistId, int songId) {
        return playlistService.addSongToPlaylist(playlistId, songId);
    }

    public boolean handleRemoveSongFromPlaylist(int playlistId, int songId) {
        return playlistService.removeSongFromPlaylist(playlistId, songId);
    }

    public List<Song> handleGetSongsFromPlaylist(int playlistId) {
        return playlistService.getSongsFromPlaylist(playlistId);
    }
}
