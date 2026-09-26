package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.model.Album;

import java.util.List;
import java.util.Scanner;

public class AlbumView {

    private final AlbumController albumController;
    private final Scanner scanner;

    public AlbumView(AlbumController albumController, Scanner scanner) {
        this.albumController = albumController;
        this.scanner = scanner;
    }

    public void run() {
        int choice;

        do {
            printMenu();

            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllAlbums();
                case 2 -> searchAlbum();
                case 3 -> addAlbum();
                case 4 -> updateAlbum();
                case 5 -> archiveAlbum();
                case 6 -> restoreAlbum();
                case 7 -> deleteAlbum();
                case 8 -> viewAllArchivedAlbums();
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
        System.out.println("\n----- Album Management -----");
        System.out.println("1. View All Albums");
        System.out.println("2. Search Album");
        System.out.println("3. Add Album");
        System.out.println("4. Update Album");
        System.out.println("5. Archive Album");
        System.out.println("6. Restore Album");
        System.out.println("7. Delete Album");
        System.out.println("8. View All Archived Albums");
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

    private void viewAllAlbums() {
        System.out.println("\n----- View All Albums -----");

        List<Album> albums = albumController.handleViewAllAlbums();

        printAlbums(albums);
    }

    private void searchAlbum() {
        System.out.println("\n----- Search Albums -----");

        System.out.print("Enter album name: ");
        String keyword = scanner.nextLine();

        List<Album> albums = albumController.handleSearchAlbum(keyword);

        printAlbums(albums);
    }

    private void addAlbum() {
        System.out.println("\n----- Add Album -----");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        Album album = new Album(name);

        boolean isSuccess = albumController.handleCreateAlbum(album);

        System.out.println(
                isSuccess
                        ? "Album added successfully."
                        : "Failed to add album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }

    private void updateAlbum() {
        System.out.println("\n----- Update Album -----");

        // Show all albums first
        viewAllAlbums();

        System.out.print("Album ID to update: ");
        int id = readInt();

        Album current = albumController.handleGetAlbumById(id);

        if (current == null) {
            System.out.println(
                    "No album found with ID " + id +
                            ". Please check the ID and try again."
            );
            return;
        }

        System.out.println(
                "New Name [" + current.getName() +
                        "] (press Enter to keep the current): "
        );

        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            name = current.getName();
        }

        Album album = new Album(id, name);

        boolean isSuccess = albumController.handleUpdateAlbum(album);

        System.out.println(
                isSuccess
                        ? "Album updated successfully."
                        : "Failed to update album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }

    public void printAlbums(List<Album> albums) {
        if (albums.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }

        String border =
                "+" + "-".repeat(6) +
                        "+" + "-".repeat(27) +
                        "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-25s |%n", "ID", "Name");
        System.out.println(border);

        for (Album album : albums) {
            System.out.printf(
                    "| %-4s | %-25s |%n",
                    album.getId(),
                    album.getName()
            );
        }

        System.out.println(border);
    }

    private void archiveAlbum() {
        System.out.println("\n----- Archive Album -----");

        // Show all active albums
        viewAllAlbums();

        System.out.print("\nAlbum ID to archive: ");
        int id = readInt();

        Album album = albumController.handleGetAlbumById(id);

        if (album == null) {
            System.out.println("No album found with ID " + id + ".");
            return;
        }

        boolean isSuccess =
                albumController.handleArchiveAlbum(id);

        System.out.println(
                isSuccess
                        ? "Album archived successfully."
                        : "Failed to archive album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }

    private void restoreAlbum() {
        System.out.println("\n----- Restore Album -----");

        // Show archived albums
        viewAllArchivedAlbums();

        System.out.print("\nAlbum ID to restore: ");
        int id = readInt();

        boolean isSuccess =
                albumController.handleRestoreAlbum(id);

        System.out.println(
                isSuccess
                        ? "Album restored successfully."
                        : "Failed to restore album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }

    private void deleteAlbum() {
        System.out.println("\n----- Delete Album -----");

        viewAllAlbums();

        System.out.print("\nAlbum ID to delete: ");
        int id = readInt();

        Album album = albumController.handleGetAlbumById(id);

        if (album == null) {
            System.out.println("No album found with ID " + id + ".");
            return;
        }

        System.out.print(
                "Are you sure you want to permanently delete this album? (Y/N): "
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        boolean isSuccess =
                albumController.handleDeleteAlbum(id);

        System.out.println(
                isSuccess
                        ? "Album deleted successfully."
                        : "Failed to delete album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }

    private void viewAllArchivedAlbums() {
        System.out.println("\n----- View All Archived Albums -----");

        List<Album> albums =
                albumController.handleGetAllArchivedAlbums();

        printAlbums(albums);
    }
}
