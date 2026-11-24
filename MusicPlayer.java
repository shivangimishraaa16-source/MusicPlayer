
import java.util.Scanner;
import java.util.Stack;

public class MusicPlayer {

    // Node class for Circular Doubly Linked List
    static class Node {
        String title;
        Node prev;
        Node next;

        Node(String title) {
            this.title = title;
            this.prev = null;
            this.next = null;
        }
    }

    // Node class for Binary Search Tree
    static class BSTNode {
        String title;
        BSTNode left, right;

        BSTNode(String title) {
            this.title = title;
            left = right = null;
        }
    }

    // Playlist variables
    static Node head = null;
    static Node current = null;
    static int size = 0;

    // Stack for recently played songs
    static Stack<String> history = new Stack<>();

    // Root of Binary Search Tree
    static BSTNode root = null;

    // Add a song to the circular doubly linked list
    static void addSong(String title) {
        title = title.trim();

        if (title.isEmpty()) {
            System.out.println("Song title cannot be empty.");
            return;
        }

        Node newNode = new Node(title);

        if (head == null) {
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            Node last = head.prev;

            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }

        size++;
        root = insertBST(root, title);

        System.out.println("Song added: " + title);
    }

    // Insert a song title into the BST
    static BSTNode insertBST(BSTNode node, String title) {
        if (node == null) {
            return new BSTNode(title);
        }

        int comparison = title.compareToIgnoreCase(node.title);

        if (comparison < 0) {
            node.left = insertBST(node.left, title);
        } else if (comparison > 0) {
            node.right = insertBST(node.right, title);
        }

        return node;
    }

    // Search for a song in the BST
    static boolean searchBST(BSTNode node, String title) {
        if (node == null) {
            return false;
        }

        int comparison = title.compareToIgnoreCase(node.title);

        if (comparison == 0) {
            return true;
        } else if (comparison < 0) {
            return searchBST(node.left, title);
        } else {
            return searchBST(node.right, title);
        }
    }

    // Play the current song
    static void playSong() {
        if (head == null) {
            System.out.println("Playlist is empty. Add songs first.");
            return;
        }

        if (current == null) {
            current = head;
        }

        System.out.println("Now playing: " + current.title);
        history.push(current.title);
    }

    // Play the next song
    static void nextSong() {
        if (head == null) {
            System.out.println("Playlist is empty. Add songs first.");
            return;
        }

        if (current == null) {
            current = head;
        } else if (size > 1) {
            current = current.next;
        }

        System.out.println("Now playing: " + current.title);
        history.push(current.title);
    }

    // Play the previous song
    static void previousSong() {
        if (head == null) {
            System.out.println("Playlist is empty. Add songs first.");
            return;
        }

        if (current == null) {
            current = head.prev;
        } else if (size > 1) {
            current = current.prev;
        }

        System.out.println("Now playing: " + current.title);
        history.push(current.title);
    }

    // Search for a song
    static void searchSong(String title) {
        if (searchBST(root, title.trim())) {
            System.out.println("Song found: " + title.trim());
        } else {
            System.out.println("Song not found: " + title.trim());
        }
    }

    // Display recently played songs
    static void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No songs played yet.");
            return;
        }

        System.out.print("Recently played: ");

        for (int i = history.size() - 1; i >= 0; i--) {
            System.out.print(history.get(i));

            if (i > 0) {
                System.out.print(", ");
            }
        }

        System.out.println();
    }

    // Display the playlist
    static void showPlaylist() {
        if (head == null) {
            System.out.println("Playlist is empty. Add songs first.");
            return;
        }

        Node temp = head;

        System.out.print("Playlist: ");

        do {
            System.out.print(temp.title);
            temp = temp.next;

            if (temp != head) {
                System.out.print(" -> ");
            }
        } while (temp != head);

        System.out.println();
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== MUSIC PLAYER =====");
            System.out.println("1. Add Song");
            System.out.println("2. Play Song");
            System.out.println("3. Next Song");
            System.out.println("4. Previous Song");
            System.out.println("5. Search Song");
            System.out.println("6. Recently Played");
            System.out.println("7. Display Playlist");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number from 1 to 8.");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter song title: ");
                    String title = sc.nextLine();
                    addSong(title);
                    break;

                case 2:
                    playSong();
                    break;

                case 3:
                    nextSong();
                    break;

                case 4:
                    previousSong();
                    break;

                case 5:
                    System.out.print("Enter song title to search: ");
                    String searchTitle = sc.nextLine();
                    searchSong(searchTitle);
                    break;

                case 6:
                    showHistory();
                    break;

                case 7:
                    showPlaylist();
                    break;

                case 8:
                    System.out.println("Exiting Music Player. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1 to 8.");
            }

        } while (true);

    }
}
