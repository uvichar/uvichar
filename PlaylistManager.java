import java.io.*;
import java.util.Scanner;

class Node {
    String song;
    Node next;
    Node prev;

    Node(String song) {
        this.song = song;
        this.next = null;
        this.prev = null;
    }
}

public class PlaylistManager {
    static Node top;
    static Node top1;

    static void tofile(String a) {
        try {
            FileWriter fw = new FileWriter("playlist.txt", true);
            fw.write(a + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    static void addNode(Node first) {
        Scanner scanner = new Scanner(System.in);
        while (first.next != null) {
            first = first.next;
        }
        first.next = new Node("");
        first.prev = first;
        first = first.next;
        System.out.print("\nEnter Song name- ");
        String a = scanner.nextLine();
        first.song = a;
        tofile(a);
        first.next = null;
    }

    static void addNodeFromFile(Node first, String a) {
        while (first.next != null) {
            first = first.next;
        }
        first.next = new Node(a);
        first.next.prev = first;
        first.next.next = null;
    }

    static void deleteFile(String a) {
        try {
            File inputFile = new File("playlist.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = a;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equals(lineToRemove)) {
                    writer.write(currentLine + "\n");
                }
            }
            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Error deleting file");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error renaming file");
            }
        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        }
    }

    static void delNode(Node first) {
        while (first.next.next != null) {
            first = first.next;
        }
        first.next = null;
        System.out.println("Deleted");
    }

    static void printList(Node first) {
        System.out.println("\nPlaylist Name- ");
        while (first.next != null) {
            System.out.println(first.song);
            first = first.next;
        }
        System.out.println(first.song);
    }

    static void countNodes(Node first) {
        int i = 0;
        while (first.next != null) {
            first = first.next;
            i++;
        }
        i++;
        System.out.println("\nTotal songs- " + (i - 1));
    }

    static Node delPos(Node pointer, int pos) {
        Node n1, prev1 = null, temp;
        int i = 0;

        if (pos == 1) {
            temp = pointer;
            deleteFile(temp.song);
            pointer = pointer.next;
            pointer.prev = null;
            System.out.println("\nThe list is updated\nUse the display function to check\n");
            return pointer;
        }
        while (i < pos - 1) {
            prev1 = pointer;
            pointer = pointer.next;
            i++;
        }

        if (pointer.next == null) {
            temp = pointer;
            deleteFile(temp.song);

            prev1.next.prev = null;
            prev1.next = null;

            System.out.println("\nThe list is updated\nUse the display function to check\n");
        } else {
            temp = pointer;
            deleteFile(temp.song);
            prev1.next = temp.next;
            temp.next.prev = prev1;
            System.out.println("\nThe list is updated\nUse the display function to check\n");
        }
        return pointer;
    }

    static void search1(Node first) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter song To be Searched- ");
        String song = scanner.nextLine();
        int flag = 0;

        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("\n#Song Found");
                flag++;
                break;
            } else {
                first = first.next;
            }
        }
        if (flag == 0) {
            System.out.println("\n#Song Not found");
        }
    }

    static void create() {
        top = null;
    }

    static void push(String data) {
        if (top == null) {
            top = new Node(data);
        } else if (!top.song.equals(data)) {
            Node temp = new Node(data);
            temp.next = top;
            top = temp;
        }
    }

    static void display() {
        top1 = top;
        if (top1 == null) {
            System.out.println("\n=>NO recently played tracks.");
            return;
        }
        System.out.println("\n#Recently played tracks-");
        while (top1 != null) {
            System.out.println(top1.song);
            top1 = top1.next;
        }
    }

    static void play(Node first) {
        Scanner scanner = new Scanner(System.in);
        printList(first);
        System.out.print("\nChoose song you wish to play- ");
        String song = scanner.nextLine();
        int flag = 0;

        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("\n=>Now Playing......" + song);
                flag++;
                push(song);
                break;
            } else {
                first = first.next;
            }
        }
        if (flag == 0) {
            System.out.println("\n#Song Not found");
        }
    }

    static void recent() {
        display();
    }

    static void topElement() {
        top1 = top;
        if (top1 == null) {
            System.out.println("\n#NO last played tracks.");
            return;
        }
        System.out.println("\n=>Last Played Song - " + top.song);
    }

    static void sort(Node pointer) {
        Node a = null;
        Node b = null;
        Node c = null;
        Node e = null;
        Node tmp = null;
        while (e != pointer.next) {
            c = a = pointer;
            b = a.next;
            while (a != e) {
                if (a.song.compareTo(b.song) > 0) {
                    if (a == pointer) {
                        tmp = b.next;
                        b.next = a;
                        a.next = tmp;
                        pointer = b;
                        c = b;
                    } else {
                        tmp = b.next;
                        b.next = a;
                        a.next = tmp;
                        c.next = b;
                        c = b;
                    }
                } else {
                    c = a;
                    a = a.next;
                }
                b = a.next;
                if (b == e) e = a;
            }
        }
    }

    static void addPlaylist(Node start) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("playlist.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                addNodeFromFile(start, line);
            }
            System.out.println("Playlist Added");
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    static void deleteSearch(Node start) {
        Scanner scanner = new Scanner(System.in);
        printList(start);
        System.out.print("\nChoose song you wish to delete- ");
        String song = scanner.nextLine();
        int flag = 0;
        while (start != null) {
            if (start.song.equals(song)) {
                System.out.println("\n#Song Found");
                Node temp = start;
                deleteFile(temp.song);
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                flag++;
                break;
            } else {
                start = start.next;
            }
        }
        if (flag == 0) {
            System.out.println("\n#Song Not found");
        }
    }

    static void deleteMenu(Node start) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which type of delete do you want?\n1.By Search\n2.By Position");
        int c = scanner.nextInt();
        switch (c) {
            case 1:
                deleteSearch(start);
                break;
            case 2:
                System.out.print("\nEnter the pos of the song : ");
                int pos = scanner.nextInt();
                delPos(start, pos - 1);
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Node start, hold;
        start = new Node("");
        System.out.println("\t\t\t**WELCOME**");
        System.out.println("\n**please use '_' for space.");
        System.out.print("\nEnter your playlist name-  ");
        start.song = scanner.nextLine();
        start.next = null;
        hold = start;
        create();

        do {
            System.out.println("\n1.Add New Song\n2.Delete Song\n3.Display Entered Playlist\n4.Total Songs\n5.Search Song\n6.Play Song\n7.Recently Played List\n8.Last Played\n9.Sorted playlist\n10.Add From File\n11.Exit");
            System.out.print("\nEnter your choice- ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addNode(start);
                    break;
                case 2:
                    deleteMenu(start);
                    break;
                case 3:
                    printList(start);
                    break;
                case 4:
                    countNodes(hold);
                    break;
                case 5:
                    search1(start);
                    break;
                case 6:
                    play(start);
                    break;
                case 7:
                    recent();
                    break;
                case 8:
                    topElement();
                    break;
                case 9:
                    sort(start.next);
                    printList(start);
                    break;
                case 10:
                    addPlaylist(start);
                    break;
                case 11:
                    System.exit(0);
            }
        } while (choice != 11);
    }
}
