package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.service.AlbumService;

import java.util.List;

public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // READ
    public List<Album> handleViewAllAlbums() {
        return albumService.getAllAlbums();
    }

    public Album handleGetAlbumById(int id) {
        return albumService.getAlbumById(id);
    }

    public List<Album> handleSearchAlbum(String keyword) {
        return albumService.searchAlbum(keyword);
    }

    // CREATE
    public boolean handleCreateAlbum(Album album) {
        return albumService.createAlbum(album);
    }

    // UPDATE
    public boolean handleUpdateAlbum(Album album) {
        return albumService.updateAlbum(album);
    }

    // ARCHIVE AND RESTORE
    public boolean handleArchiveAlbum(int id) {
        return albumService.archiveAlbum(id);
    }

    public boolean handleRestoreAlbum(int id) {
        return albumService.restoreAlbum(id);
    }

    // DELETE
    public boolean handleDeleteAlbum(int id) {
        return albumService.deleteAlbum(id);
    }

    public List<Album> handleGetAllArchivedAlbums() {
        return albumService.getAllArchivedAlbums();
    }
}
