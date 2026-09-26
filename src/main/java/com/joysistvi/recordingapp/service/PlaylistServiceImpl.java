package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.PlaylistRepo;

import java.util.List;

public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepo playlistRepo; // Composition

    // Constructor injection
    public PlaylistServiceImpl(PlaylistRepo playlistRepo) {
        this.playlistRepo = playlistRepo;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepo.getAllPlaylists();
    }

    @Override
    public Playlist getPlaylistById(int id) {
        if (id <= 0) {
            System.out.println("Invalid playlist ID.");
            return null;
        }

        Playlist playlist = playlistRepo.getPlaylistById(id);

        if (playlist == null) {
            System.out.println("Playlist not found.");
        }

        return playlist;
    }

    @Override
    public List<Playlist> searchPlaylist(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }

        return playlistRepo.searchPlaylist(keyword.trim());
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {

        if (playlist == null) {
            System.out.println("Playlist object cannot be null.");
            return false;
        }

        if (playlist.getName() == null || playlist.getName().trim().isEmpty()) {
            System.out.println("Playlist name is required.");
            return false;
        }

        playlist.setName(playlist.getName().trim());

        return playlistRepo.createPlaylist(playlist);
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {

        if (playlist == null || playlist.getId() <= 0) {
            System.out.println("Invalid playlist data for update.");
            return false;
        }

        if (playlist.getName() == null || playlist.getName().trim().isEmpty()) {
            System.out.println("Playlist name cannot be empty.");
            return false;
        }

        playlist.setName(playlist.getName().trim());

        return playlistRepo.updatePlaylist(playlist);
    }

    @Override
    public boolean archivePlaylist(int id) {

        if (id <= 0) {
            System.out.println("Invalid playlist ID for archive.");
            return false;
        }

        return playlistRepo.archivePlaylist(id);
    }

    @Override
    public boolean restorePlaylist(int id) {

        if (id <= 0) {
            System.out.println("Invalid playlist ID for restore.");
            return false;
        }

        return playlistRepo.restorePlaylist(id);
    }

    @Override
    public boolean deletePlaylist(int id) {

        if (id <= 0) {
            System.out.println("Invalid playlist ID for deletion.");
            return false;
        }

        return playlistRepo.deletePlaylist(id);
    }

    @Override
    public List<Playlist> getAllArchivedPlaylists() {
        return playlistRepo.getAllArchivedPlaylists();
    }

    // =========================
    // PLAYLIST SONGS
    // =========================

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {

        if (playlistId <= 0) {
            System.out.println("Invalid playlist ID.");
            return false;
        }

        if (songId <= 0) {
            System.out.println("Invalid song ID.");
            return false;
        }

        return playlistRepo.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {

        if (playlistId <= 0) {
            System.out.println("Invalid playlist ID.");
            return false;
        }

        if (songId <= 0) {
            System.out.println("Invalid song ID.");
            return false;
        }

        return playlistRepo.removeSongFromPlaylist(playlistId, songId);
    }

    @Override
    public List<Song> getSongsFromPlaylist(int playlistId) {

        if (playlistId <= 0) {
            System.out.println("Invalid playlist ID.");
            return List.of();
        }

        return playlistRepo.getSongsFromPlaylist(playlistId);
    }
}
