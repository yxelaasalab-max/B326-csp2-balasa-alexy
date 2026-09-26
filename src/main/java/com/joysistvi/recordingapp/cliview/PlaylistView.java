package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController playlistController;
    private final Scanner scanner;

    public PlaylistView(PlaylistController playlistController, Scanner scanner) {
        this.playlistController = playlistController;
        this.scanner = scanner;
    }

    public void run() {
        int choice;

        do {
            printMenu();

            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllPlaylists();
                case 2 -> searchPlaylist();
                case 3 -> addPlaylist();
                case 4 -> updatePlaylist();
                case 5 -> archivePlaylist();
                case 6 -> restorePlaylist();
                case 7 -> deletePlaylist();
                case 8 -> viewAllArchivedPlaylists();
                case 9 -> addSongToPlaylist();
                case 10 -> removeSongFromPlaylist();
                case 11 -> viewSongsFromPlaylist();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("\n----- Playlist Management -----");
        System.out.println("1. View All Playlists");
        System.out.println("2. Search Playlist");
        System.out.println("3. Add Playlist");
        System.out.println("4. Update Playlist");
        System.out.println("5. Archive Playlist");
        System.out.println("6. Restore Playlist");
        System.out.println("7. Delete Playlist");
        System.out.println("8. View All Archived Playlists");
        System.out.println("9. Add Song to Playlist");
        System.out.println("10. Remove Song from Playlist");
        System.out.println("11. View Songs from Playlist");
        System.out.println("0. Back");
    }

    public int promptChoice() {
        System.out.print("Choice: ");
        return readInt();
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input.trim());
            } catch (RuntimeException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // =========================
    // VIEW ALL PLAYLISTS
    // =========================

    private void viewAllPlaylists() {
        System.out.println("\n----- View All Playlists -----");

        List<Playlist> playlists = playlistController.handleViewAllPlaylists();

        printPlaylists(playlists);
    }

    // =========================
    // SEARCH PLAYLIST
    // =========================

    private void searchPlaylist() {
        System.out.println("\n----- Search Playlists -----");

        System.out.print("Enter name: ");
        String keyword = scanner.nextLine();

        List<Playlist> playlists = playlistController.handleSearchPlaylist(keyword);

        printPlaylists(playlists);
    }

    // =========================
    // ADD PLAYLIST
    // =========================

    private void addPlaylist() {
        System.out.println("\n----- Add Playlist -----");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        Playlist playlist = new Playlist(name);

        boolean isSuccess = playlistController.handleCreatePlaylist(playlist);

        System.out.println(isSuccess ? "Playlist added successfully." : "Failed to add playlist.");

        if (isSuccess) {
            System.out.println();
            viewAllPlaylists();
        }
    }

    // =========================
    // UPDATE PLAYLIST
    // =========================

    private void updatePlaylist() {
        System.out.println("\n----- Update Playlist -----");

        viewAllPlaylists();

        System.out.print("Playlist ID to update: ");
        int id = readInt();

        Playlist current = playlistController.handleGetPlaylistById(id);

        if (current == null) {
            System.out.println("No playlist found with ID " + id + ". Please check the ID and try again.");
            return;
        }

        System.out.println("New Name [" + current.getName() + "] (press Enter to keep the current): ");

        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            name = current.getName();
        }

        Playlist playlist = new Playlist(id, name);

        boolean isSuccess =
                playlistController.handleUpdatePlaylist(playlist);

        System.out.println(isSuccess ? "Playlist updated successfully." : "Failed to update playlist.");

        if (isSuccess) {
            System.out.println();
            viewAllPlaylists();
        }
    }

    // =========================
    // PRINT PLAYLISTS
    // =========================

    public void printPlaylists(List<Playlist> playlists) {

        if (playlists.isEmpty()) {
            System.out.println("No playlists found.");
            return;
        }

        String border = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-25s |%n", "ID", "Name");
        System.out.println(border);

        for (Playlist playlist : playlists) {
            System.out.printf("| %-4s | %-25s |%n", playlist.getId(), playlist.getName());
        }

        System.out.println(border);
    }

    // =========================
    // ARCHIVE PLAYLIST
    // =========================

    private void archivePlaylist() {
        System.out.println("\n----- Archive Playlist -----");

        viewAllPlaylists();

        System.out.print("\nPlaylist ID to archive: ");
        int id = readInt();

        Playlist playlist =
                playlistController.handleGetPlaylistById(id);

        if (playlist == null) {
            System.out.println("No playlist found with ID " + id + ".");
            return;
        }

        boolean isSuccess =
                playlistController.handleArchivePlaylist(id);

        System.out.println(isSuccess ? "Playlist archived successfully." : "Failed to archive playlist.");

        if (isSuccess) {
            System.out.println();
            viewAllPlaylists();
        }
    }

    // =========================
    // RESTORE PLAYLIST
    // =========================

    private void restorePlaylist() {
        System.out.println("\n----- Restore Playlist -----");

        viewAllArchivedPlaylists();

        System.out.print("\nPlaylist ID to restore: ");
        int id = readInt();

        boolean isSuccess = playlistController.handleRestorePlaylist(id);

        System.out.println(isSuccess ? "Playlist restored successfully." : "Failed to restore playlist.");

        if (isSuccess) {
            System.out.println();
            viewAllPlaylists();
        }
    }

    // =========================
    // DELETE PLAYLIST
    // =========================

    private void deletePlaylist() {
        System.out.println("\n----- Delete Playlist -----");

        viewAllPlaylists();

        System.out.print("\nPlaylist ID to delete: ");
        int id = readInt();

        Playlist playlist = playlistController.handleGetPlaylistById(id);

        if (playlist == null) {
            System.out.println("No playlist found with ID " + id + ".");
            return;
        }

        System.out.print(
                "Are you sure you want to permanently delete this playlist? (Y/N): "
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        boolean isSuccess =
                playlistController.handleDeletePlaylist(id);

        System.out.println(isSuccess ? "Playlist deleted successfully." : "Failed to delete playlist.");

        if (isSuccess) {
            System.out.println();
            viewAllPlaylists();
        }
    }

    // =========================
    // VIEW ARCHIVED PLAYLISTS
    // =========================

    private void viewAllArchivedPlaylists() {
        System.out.println("\n----- View All Archived Playlists -----");

        List<Playlist> playlists =
                playlistController.handleGetAllArchivedPlaylists();

        printPlaylists(playlists);
    }

    // ==================================================
    // ADD SONG TO PLAYLIST
    // ==================================================

    private void addSongToPlaylist() {
        System.out.println("\n----- Add Song to Playlist -----");

        viewAllPlaylists();

        System.out.print("\nPlaylist ID: ");
        int playlistId = readInt();

        Playlist playlist = playlistController.handleGetPlaylistById(playlistId);

        if (playlist == null) {
            System.out.println("No playlist found with ID " + playlistId + ".");
            return;
        }

        System.out.print("Song ID to add: ");
        int songId = readInt();

        boolean isSuccess = playlistController.handleAddSongToPlaylist(playlistId, songId);

        System.out.println(isSuccess ? "Song added to playlist successfully." : "Failed to add song to playlist.");

        if (isSuccess) {
            System.out.println();
            viewSongsFromPlaylist(playlistId);
        }
    }

    // ==================================================
    // REMOVE SONG FROM PLAYLIST
    // ==================================================

    private void removeSongFromPlaylist() {
        System.out.println("\n----- Remove Song from Playlist -----");

        viewAllPlaylists();

        System.out.print("\nPlaylist ID: ");
        int playlistId = readInt();

        Playlist playlist = playlistController.handleGetPlaylistById(playlistId);

        if (playlist == null) {
            System.out.println("No playlist found with ID " + playlistId + ".");
            return;
        }

        viewSongsFromPlaylist(playlistId);

        System.out.print("\nSong ID to remove: ");
        int songId = readInt();

        boolean isSuccess = playlistController.handleRemoveSongFromPlaylist(playlistId, songId);

        System.out.println(isSuccess ? "Song removed from playlist successfully." : "Failed to remove song from playlist.");

        if (isSuccess) {
            System.out.println();
            viewSongsFromPlaylist(playlistId);
        }
    }

    // ==================================================
    // VIEW SONGS FROM PLAYLIST
    // ==================================================

    private void viewSongsFromPlaylist() {
        System.out.println("\n----- View Songs from Playlist -----");

        viewAllPlaylists();

        System.out.print("\nPlaylist ID: ");
        int playlistId = readInt();

        Playlist playlist = playlistController.handleGetPlaylistById(playlistId);

        if (playlist == null) {
            System.out.println("No playlist found with ID " + playlistId + ".");
            return;
        }

        viewSongsFromPlaylist(playlistId);
    }

    private void viewSongsFromPlaylist(int playlistId) {

        List<Song> songs = playlistController.handleGetSongsFromPlaylist(playlistId);

        if (songs.isEmpty()) {
            System.out.println("No songs found in this playlist.");
            return;
        }

        System.out.println("\n----- Songs in Playlist -----");

        String border = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-25s |%n", "ID", "Name");
        System.out.println(border);

        for (Song song : songs) {
            System.out.printf("| %-4s | %-25s |%n", song.getId(), song.getName());
        }

        System.out.println(border);
    }
}
