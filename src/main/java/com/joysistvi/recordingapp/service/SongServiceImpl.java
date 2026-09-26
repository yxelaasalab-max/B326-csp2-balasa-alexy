package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.SongRepo;

import java.util.List;

public class SongServiceImpl implements SongService {

    private final SongRepo songRepo; // Composition

    // Constructor injection
    public SongServiceImpl(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepo.getAllSongs();
    }

    @Override
    public Song getSongById(int id) {
        if (id <= 0) {
            System.out.println("Invalid song ID.");
            return null;
        }

        Song song = songRepo.getSongById(id);

        if (song == null) {
            System.out.println("Song not found.");
        }

        return song;
    }

    @Override
    public List<Song> searchSong(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }

        return songRepo.searchSong(keyword.trim());
    }

    @Override
    public boolean createSong(Song song) {

        if (song == null) {
            System.out.println("Song object cannot be null.");
            return false;
        }

        if (song.getName() == null || song.getName().trim().isEmpty()) {
            System.out.println("Song name is required.");
            return false;
        }

        song.setName(song.getName().trim());

        return songRepo.createSong(song);
    }

    @Override
    public boolean updateSong(Song song) {

        if (song == null || song.getId() <= 0) {
            System.out.println("Invalid song data for update.");
            return false;
        }

        if (song.getName() == null || song.getName().trim().isEmpty()) {
            System.out.println("Song name cannot be empty.");
            return false;
        }

        song.setName(song.getName().trim());

        return songRepo.updateSong(song);
    }

    @Override
    public boolean archiveSong(int id) {

        if (id <= 0) {
            System.out.println("Invalid song ID for archive.");
            return false;
        }

        return songRepo.archiveSong(id);
    }

    @Override
    public boolean restoreSong(int id) {

        if (id <= 0) {
            System.out.println("Invalid song ID for restore.");
            return false;
        }

        return songRepo.restoreSong(id);
    }

    @Override
    public boolean deleteSong(int id) {

        if (id <= 0) {
            System.out.println("Invalid song ID for deletion.");
            return false;
        }

        return songRepo.deleteSong(id);
    }

    @Override
    public List<Song> getAllArchivedSongs() {
        return songRepo.getAllArchivedSongs();
    }
}
