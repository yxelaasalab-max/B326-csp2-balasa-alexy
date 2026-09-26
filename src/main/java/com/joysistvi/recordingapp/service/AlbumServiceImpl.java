package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepo;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepo albumRepo;

    public AlbumServiceImpl(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepo.getAllAlbums();
    }

    @Override
    public Album getAlbumById(int id) {
        if (id <= 0) {
            System.out.println("Invalid album ID.");
            return null;
        }

        Album album = albumRepo.getAlbumById(id);

        if (album == null) {
            System.out.println("Album not found.");
        }

        return album;
    }

    @Override
    public List<Album> searchAlbum(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }

        return albumRepo.searchAlbum(keyword.trim());
    }

    @Override
    public boolean createAlbum(Album album) {
        if (album == null) {
            System.out.println("Album object cannot be null.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name is required.");
            return false;
        }

        album.setName(album.getName().trim());

        return albumRepo.createAlbum(album);
    }

    @Override
    public boolean updateAlbum(Album album) {
        if (album == null || album.getId() <= 0) {
            System.out.println("Invalid album data for update.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name cannot be empty.");
            return false;
        }

        album.setName(album.getName().trim());

        return albumRepo.updateAlbum(album);
    }

    @Override
    public boolean archiveAlbum(int id) {
        if (id <= 0) {
            System.out.println("Invalid album ID for archive.");
            return false;
        }

        return albumRepo.archiveAlbum(id);
    }

    @Override
    public boolean restoreAlbum(int id) {
        if (id <= 0) {
            System.out.println("Invalid album ID for restore.");
            return false;
        }

        return albumRepo.restoreAlbum(id);
    }

    @Override
    public boolean deleteAlbum(int id) {
        if (id <= 0) {
            System.out.println("Invalid album ID for deletion.");
            return false;
        }

        return albumRepo.deleteAlbum(id);
    }

    @Override
    public List<Album> getAllArchivedAlbums() {
        return albumRepo.getAllArchivedAlbums();
    }
}