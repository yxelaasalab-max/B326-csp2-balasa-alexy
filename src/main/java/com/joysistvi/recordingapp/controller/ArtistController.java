package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.service.ArtistService;

import java.util.List;

public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // READ
    public List<Artist> handleViewAllArtists () {
        return artistService.getAllArtists();
    }

    public Artist handleGetAllArtistById(int id) {
        return artistService.getArtistById(id);
    }

    public List<Artist> handleSearchArtist(String keyword) {
        return artistService.searchArtist(keyword);
    }

    // CREATE
    public boolean handleCreateArtist(Artist artist) {
        return artistService.createArtist(artist);
    }

    // RESTORE
    public boolean handleUpdateArtist(Artist artist) {
        return artistService.updateArtist(artist);
    }

    // ARCHIVE AND RESTORE
    public boolean handleArchiveArtist(int id) {
        return artistService.archiveArtist(id);
    }

    public boolean handleRestoreArtist(int id) {
        return artistService.restoreArtist(id);
    }

    // DELETE
    public boolean handleDeleteArtist(int id) {
        return artistService.deleteArtist(id);
    }

    public List<Artist> handleGetAllArchivedArtists() {
        return artistService.getAllArchivedArtists();
    }
}
