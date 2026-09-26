package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepoImpl implements SongRepo {

    private final DbConnection dbConnection;

    public SongRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                songs.add(new Song(result.getInt("id"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Songs Error: " + e.getMessage());
        }

        return songs;
    }

    @Override
    public Song getSongById(int id) {
        String query = "SELECT * FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet res = prep.executeQuery()) {
                if (res.next()) {
                    return new Song(res.getInt("id"), res.getString("name"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Read Song By Id Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Song> searchSong(String keyword) {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs " + "WHERE name LIKE ? AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    songs.add(new Song(res.getInt("id"), res.getString("name")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Song Error: " + e.getMessage());
        }

        return songs;
    }

    @Override
    public boolean createSong(Song song) {
        String query = "INSERT INTO songs (name) VALUES (?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getName());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create Song Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateSong(Song song) {
        String query = "UPDATE songs SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getName());
            prep.setInt(2, song.getId());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Update Song Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean archiveSong(int id) {
        String query = "UPDATE songs SET is_archived = 1 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Archive Song Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean restoreSong(int id) {
        String query = "UPDATE songs SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Restore Song Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteSong(int id) {
        String query = "DELETE FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Song Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public List<Song> getAllArchivedSongs() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                songs.add(new Song(result.getInt("id"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Archived Songs Error: " + e.getMessage());
        }

        return songs;
    }
}
