package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepoImpl implements AlbumRepo {

    private final DbConnection dbConnection;

    public AlbumRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                albums.add(new Album(result.getInt("id"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Albums Error: " + e.getMessage());
        }

        return albums;
    }

    @Override
    public Album getAlbumById(int id) {
        String query = "SELECT * FROM albums WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet res = prep.executeQuery()) {
                if (res.next()) {
                    return new Album(res.getInt("id"), res.getString("name"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Read Album By Id Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Album> searchAlbum(String keyword) {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums WHERE name LIKE ? AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    albums.add(new Album(res.getInt("id"), res.getString("name")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Album Error: " + e.getMessage());
        }

        return albums;
    }

    @Override
    public boolean createAlbum(Album album) {
        String query = "INSERT INTO albums (name) VALUES (?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, album.getName());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create Album Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateAlbum(Album album) {
        String query = "UPDATE albums SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, album.getName());
            prep.setInt(2, album.getId());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Update Album Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean archiveAlbum(int id) {
        String query = "UPDATE albums SET is_archived = 1 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Archive Album Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean restoreAlbum(int id) {
        String query = "UPDATE albums SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Restore Album Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteAlbum(int id) {
        String query = "DELETE FROM albums WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Album Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public List<Album> getAllArchivedAlbums() {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                albums.add(new Album(result.getInt("id"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Archived Albums Error: " + e.getMessage());
        }

        return albums;
    }
}