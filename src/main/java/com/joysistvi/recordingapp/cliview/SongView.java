package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class SongView {

    private final SongController songController;
    private final Scanner scanner;

    public SongView(SongController songController, Scanner scanner) {
        this.songController = songController;
        this.scanner = scanner;
    }

    public void run() {
        int choice;

        do {
            printMenu();

            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllSongs();
                case 2 -> searchSong();
                case 3 -> addSong();
                case 4 -> updateSong();
                case 5 -> archiveSong();
                case 6 -> restoreSong();
                case 7 -> deleteSong();
                case 8 -> viewAllArchivedSongs();
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
        System.out.println("\n----- Song Management -----");
        System.out.println("1. View All Songs");
        System.out.println("2. Search Song");
        System.out.println("3. Add Song");
        System.out.println("4. Update Song");
        System.out.println("5. Archive Song");
        System.out.println("6. Restore Song");
        System.out.println("7. Delete Song");
        System.out.println("8. View All Archived Songs");
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

    private void viewAllSongs() {
        System.out.println("\n----- View All Songs -----");

        List<Song> songs = songController.handleViewAllSongs();

        printSongs(songs);
    }

    private void searchSong() {
        System.out.println("\n----- Search Songs -----");

        System.out.print("Enter name: ");
        String keyword = scanner.nextLine();

        List<Song> songs = songController.handleSearchSong(keyword);

        printSongs(songs);
    }

    private void addSong() {
        System.out.println("\n----- Add Song -----");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        Song song = new Song(name);

        boolean isSuccess = songController.handleCreateSong(song);

        System.out.println(isSuccess ? "Song added successfully." : "Failed to add song.");

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }

    private void updateSong() {
        System.out.println("\n----- Update Song -----");

        // Show all songs first
        viewAllSongs();

        System.out.print("Song ID to update: ");
        int id = readInt();

        Song current = songController.handleGetSongById(id);

        if (current == null) {
            System.out.println("No song found with ID " + id + ". Please check the ID and try again.");
            return;
        }

        System.out.println("New Name [" + current.getName() + "] (press Enter to keep the current): ");

        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            name = current.getName();
        }

        Song song = new Song(id, name);

        boolean isSuccess = songController.handleUpdateSong(song);

        System.out.println(
                isSuccess ? "Song updated successfully." : "Failed to update song.");

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }

    public void printSongs(List<Song> songs) {
        if (songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        String border = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-25s |%n", "ID", "Name");
        System.out.println(border);

        for (Song song : songs) {
            System.out.printf("| %-4s | %-25s |%n", song.getId(), song.getName());
        }

        System.out.println(border);
    }

    private void archiveSong() {
        System.out.println("\n----- Archive Song -----");

        // Show all active songs first
        viewAllSongs();

        System.out.print("\nSong ID to archive: ");
        int id = readInt();

        Song song = songController.handleGetSongById(id);

        if (song == null) {
            System.out.println("No song found with ID " + id + ".");
            return;
        }

        boolean isSuccess = songController.handleArchiveSong(id);

        System.out.println(isSuccess ? "Song archived successfully." : "Failed to archive song.");

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }

    private void restoreSong() {
        System.out.println("\n----- Restore Song -----");

        // Show archived songs first
        viewAllArchivedSongs();

        System.out.print("\nSong ID to restore: ");
        int id = readInt();

        boolean isSuccess = songController.handleRestoreSong(id);

        System.out.println(isSuccess ? "Song restored successfully." : "Failed to restore song.");

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }

    private void deleteSong() {
        System.out.println("\n----- Delete Song -----");

        viewAllSongs();

        System.out.print("\nSong ID to delete: ");
        int id = readInt();

        Song song = songController.handleGetSongById(id);

        if (song == null) {
            System.out.println("No song found with ID " + id + ".");
            return;
        }

        System.out.print(
                "Are you sure you want to permanently delete this song? (Y/N): "
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        boolean isSuccess = songController.handleDeleteSong(id);

        System.out.println(isSuccess ? "Song deleted successfully." : "Failed to delete song.");

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }

    private void viewAllArchivedSongs() {
        System.out.println("\n----- View All Archived Songs -----");

        List<Song> songs =
                songController.handleGetAllArchivedSongs();

        printSongs(songs);
    }
}
