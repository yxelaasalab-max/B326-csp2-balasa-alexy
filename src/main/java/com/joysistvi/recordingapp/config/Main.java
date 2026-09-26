package com.joysistvi.recordingapp.config;

import com.joysistvi.recordingapp.dao.ArtistDao;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends DbConnection {

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        ArtistDao artistDao = new ArtistDao(dbConnection);
        artistDao.searchArtist("Chris Brown");
    }
}

/* Example:
public static void main(String[] args) {
DbConnection db = new DbConnection();

        try {
            Connection conn = db.connect();
            System.out.println("Connected Successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
}
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

// CRUD Operation

public void createArtist() {
        // Define Query

        try {
            Connection conn = db.connect();
            conn.createStatement();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 */