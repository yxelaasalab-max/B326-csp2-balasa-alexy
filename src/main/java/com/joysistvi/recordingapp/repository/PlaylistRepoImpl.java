package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepoImpl implements PlaylistRepo {

    private final DbConnection dbConnection;

    public PlaylistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                playlists.add(new Playlist(
                        result.getInt("id"),
                        result.getString("name")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Get All Playlists Error: " + e.getMessage());
        }

        return playlists;
    }

    @Override
    public Playlist getPlaylistById(int id) {
        String query = "SELECT * FROM playlists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet res = prep.executeQuery()) {
                if (res.next()) {
                    return new Playlist(
                            res.getInt("id"),
                            res.getString("name")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Read Playlist By Id Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Playlist> searchPlaylist(String keyword) {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists " + "WHERE name LIKE ? AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    playlists.add(new Playlist(res.getInt("id"), res.getString("name")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Playlist Error: " + e.getMessage());
        }

        return playlists;
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {
        String query = "INSERT INTO playlists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, playlist.getName());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create Playlist Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {
        String query = "UPDATE playlists SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, playlist.getName());
            prep.setInt(2, playlist.getId());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Update Playlist Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean archivePlaylist(int id) {
        String query = "UPDATE playlists SET is_archived = 1 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Archive Playlist Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean restorePlaylist(int id) {
        String query = "UPDATE playlists SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Restore Playlist Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deletePlaylist(int id) {
        String query = "DELETE FROM playlists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Playlist Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public List<Playlist> getAllArchivedPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                playlists.add(new Playlist(result.getInt("id"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Archived Playlists Error: " + e.getMessage());
        }

        return playlists;
    }

    // =========================
    // PLAYLIST SONGS
    // =========================

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        String query = "INSERT INTO playlist_songs (playlist_id, song_id) " + "VALUES (?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);
            prep.setInt(2, songId);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Add Song To Playlist Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        String query = "DELETE FROM playlist_songs " +
                "WHERE playlist_id = ? AND song_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);
            prep.setInt(2, songId);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Remove Song From Playlist Error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public List<Song> getSongsFromPlaylist(int playlistId) {
        List<Song> songs = new ArrayList<>();

        String query =
                "SELECT s.id, s.name " + "FROM songs s " + "INNER JOIN playlist_songs ps ON s.id = ps.song_id " + "WHERE ps.playlist_id = ? " + "AND s.is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {
                    songs.add(new Song(result.getInt("id"), result.getString("name")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Songs From Playlist Error: " + e.getMessage());
        }

        return songs;
    }
}
