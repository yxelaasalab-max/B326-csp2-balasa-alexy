package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepoImpl implements ArtistRepo {

    private final DbConnection dbConnection;

    public ArtistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        // try-with resources for auto-closing database connection
        try (Connection conn = dbConnection.connect(); // connect db
             Statement stmnt = conn.createStatement(); // create statement
             ResultSet result = stmnt.executeQuery(query);) { // execute query
            // Header
            System.out.printf("%-5s | %-20s%n", "ID", "Name");
            System.out.println("-".repeat(26));

            // extract and display data rows
            while (result.next()) {
                artists.add(new Artist(result.getString("name"), result.getInt("id")));
            }

        }catch (SQLException e) {
            System.err.println("Get all Artists: " + e.getMessage());
        }

        return artists; // null

    }

    public Artist getArtistById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                return new Artist(res.getInt("id"), res.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Read Artist By Id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean createArtist(Artist artist) {

        // parameterized query
        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep = conn.prepareStatement(query);

            // set wild card values
            prep.setString(1, artist.getName());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;
        }catch (Exception e) {
            System.err.println("Create Artist: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Artist> searchArtist(String keyword) {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE name LIKE ?";

        // create a statement
        // try with resources
        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            // extract data
            while (result.next()) {
                artists.add(new Artist(result.getInt("id"), result.getString( "%" + "name" + "%")));
            }

        } catch(SQLException e) {
            System.err.println("Get All Artist: " + e.getMessage());
        }

        return artists;
    }

    @Override
    public boolean updateArtist(Artist artist) {
        String query = "UPDATE songs SET name = ? WHERE id = ?";

        try(Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query)){


            prep.setString(1, artist.getName());
            prep.setInt(2, artist.getName());

            int rows = prep.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Update Artist: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean archiveArtist(int id) {
        String query = "UPDATE artists SET is_archived = 1 WHERE id = ?";

        try(Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query)){


            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            return rows > 0;

        } catch(SQLException e) {
            System.err.println("Archive Artist: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean restoreArtist(int id) {
        String query = "UPDATE artists SET is_archived = 0 WHERE id = ?";

        try(Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query)) {


            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            return rows > 0;

        } catch(SQLException e) {
            System.err.println("Restore Artist: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteArtist(int id) {
        String query = "DELETE FROM artists WHERE id = ?";

        try(Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query)) {


            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            return rows > 0;

        } catch(SQLException e) {
            System.err.println("Delete Artist: " + e.getMessage());
        }

        return false;
    }

    @Override
    public List<Artist> readAllArchivedArtist() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 1";

        // create a statement
        // try with resources
        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {



            // extract data
            while (result.next()) {
                artists.add(new Artist(result.getInt("id"), result.getString("name")));

            }

        } catch(SQLException e) {
            System.err.println("Get All Artist: " + e.getMessage());
        }

        return artists;
    }
}
