package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;

import java.sql.*;

/**
 * DATA ACCESS OBJECT (DAO) PATTERN EXPLANATION
 * --------------------------------------------
 *
 * WHAT IS A DAO?
 * DAO stands for Data Access Object. It is a fundamental design pattern in
 * Java used to separate low-level data accessing operations (SQL queries)
 * from high-level business logic.
 *
 * PURPOSE OF A DAO CLASS:
 * 1. Abstraction & Decoupling:
 *    It acts as a "middleman" between your Java Application and the Database.
 *    The rest of your code calls simple Java methods (e.g., songDao.addSong(song))
 *    without needing to know the underlying SQL queries or database details.
 *
 * 2. Centralized Database Operations (CRUD):
 *    All Create, Read, Update, and Delete operations for a specific entity
 *    (like a 'Song' or 'User') are organized in one dedicated class instead
 *    of being scattered throughout the project.
 *
 * 3. Maintainability & Flexibility:
 *    If the database structure, SQL queries, or database type (e.g., switching
 *    from MySQL to PostgreSQL) change in the future, you only need to modify
 *    the DAO class rather than touching the entire application codebase.
 */

// inheritance: is-a relationship (tightly coupled)
// composition: has-a relationship (loose coupled)
public class ArtistDao {

    // Composition
    private final DbConnection dbConnection;

    // Constructor Injection
    public ArtistDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // CRUD Operation

    public void readAllArtists() {
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        // create statement
        // try-with resources
        try (Connection conn = dbConnection.connect(); // connect db
             Statement stmnt = conn.createStatement(); // create statement
             ResultSet result = stmnt.executeQuery(query);) { // execute query
            // Top Border
            System.out.println("+-------+----------------------+");
            // Header
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            // Header Divider
            System.out.println("+-------+----------------------+");

            // Extract and display data rows
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");

                System.out.printf("| %-5d | %-20s |%n", id, name);
            }
            // Bottom Border
            System.out.println("+-------+----------------------+");

        }catch (SQLException e) {
            System.err.println("Get all Artists: " + e.getMessage());
        }
    }

    public void createArtist(String name) {
        // Validation
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return;
        }

        // parameterized query
        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep = conn.prepareStatement(query);

            // set wild card values
            prep.setString(1, name);

            int rows = prep.executeUpdate();

            // synchronization
            System.out.println(rows > 0 ? "Artist Added successfully.\n": "Failed to add artist.");
            readAllArtists();
        }catch (Exception e) {
            System.err.println("Create Artist: " + e.getMessage());
        }
    }

    public void updateArtist(String name, int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return;
        }

        String query = " UPDATE artists SET name = ? WHERE id = ?";

        try {
            Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setString(1, name);
            prep.setInt(2, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist " + name + " updated succesfully" : "Failed to update artist");

            System.out.println();
            readAllArtists();
        }catch (SQLException e) {
            System.out.println("Update Artist: " + e.getMessage());
        }
    }

    public void archiveArtist (int id) {
        String query = "UPDATE artists SET is_archived = 1 WHERE id = ?";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep =  conn.prepareStatement(query);

            // set wild card values
            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            // synchronization
            System.out.println(rows > 0 ? "Artist " + id + " archive successfully.\n": "Failed to archive artist.");
            readAllArtists();
        } catch (Exception e) {
            System.err.println("Archive Artist: " + e.getMessage());
        }
    }

    public void restoreArtist (int id) {

        String query = "UPDATE artists SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep =  conn.prepareStatement(query);

            // set wild card values
            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            // synchronization
            System.out.println(rows > 0 ? "Artist " + id + " restored successfully.\n": "Failed to archive artist.");
            readAllArtists();
        } catch (Exception e) {
            System.err.println("Archive Artist: " + e.getMessage());
        }
    }

    public void deleteArtist (int id) {
        String query = "DELETE FROM artist WHERE id = ?";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep =  conn.prepareStatement(query);

            // set wild card values
            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            // synchronization
            System.out.println(rows > 0 ? "Artist " + id + " deleted successfully.\n": "Failed to deleted artist.");
            readAllArtists();
        } catch (Exception e) {
            System.err.println("Archive Artist: " + e.getMessage());
        }
    }

    public void readArtistById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            System.out.println("+-------+----------------------+");
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            System.out.println("+-------+----------------------+");

            if (res.next()) {
                System.out.printf("| %-5d | %-20s |%n", res.getInt("id"), res.getString("name"));
            }

            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.out.println("Read Artist By Id: " + e.getMessage());
        }
    }

    // SQC Principle
    public void searchArtist(String keyword) {
        // Validation logic mixed right in with the query — this is what will
        // move to a Service class later.
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        String query = "SELECT id, name FROM artists WHERE name LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword.trim() + "%");
            ResultSet res = prep.executeQuery();

            System.out.println("+-------+----------------------+");
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            System.out.println("+-------+----------------------+");

            while (res.next()) {
                System.out.printf("| %-5d | %-20s |%n", res.getInt("id"), res.getString("name"));
            }

            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.out.println("Search Artist: " + e.getMessage());
        }
    }

    public void readAllArchivedArtists() {
        String query = "SELECT * FROM artists WHERE is_archived = 1";

        // create statement
        // try-with resources
        try (Connection conn = dbConnection.connect(); // connect db
             Statement stmnt = conn.createStatement(); // create statement
             ResultSet result = stmnt.executeQuery(query);) { // execute query
            // Header
            System.out.printf("%-5s | %-20s%n", "ID", "Name");
            System.out.println("-".repeat(26));
            // extract data
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");

                System.out.printf("%-5d | %-20s%n", id, name);
            }

        }catch (SQLException e) {
            System.err.println("Get all Artists: " + e.getMessage());
        }
    }
}

// Hard Delete / Soft Delete (Archive)

/* Example:
    class car {
    Engine engine; // composition
}

class Vehicle {
}

class Engine {
}

++++++++++++++++++++++++++++++++++++++++++++

    SQL Categories
    DDL -> Data Definition Language
    DML -> Data Manipulation Language
           (Insert, Update, Delete) : preparedStatement() -> executeUpdate()
    DQL -> Data Query Language
           (Select) : createStatement() -> executeQuery()

+++++++++++++++++++++++++++++++++++++++++++++++++

 */