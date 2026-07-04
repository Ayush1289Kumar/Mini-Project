import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String bookName;
    private String author;
    private String issuedTo;
    private LocalDate issuedOn;

    // Constructor for creating a new library book
    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
        this.issuedTo = null; // null means not issued
        this.issuedOn = null;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public LocalDate getIssuedOn() {
        return issuedOn;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public void setIssuedOn(LocalDate issuedOn) {
        this.issuedOn = issuedOn;
    }
}

class Library {
    private ArrayList<Book> allBooks = new ArrayList<>();
    private ArrayList<Book> issuedBooks = new ArrayList<>();

    public void addBook(Book book) {
        allBooks.add(book);
        System.out.println("\nBook '" + book.getBookName() + "' added successfully!");
    }

    // Helper method to find an available book by its name
    private Book findAvailableBook(String name) {
        for (Book book : allBooks) {
            if (book.getBookName().equalsIgnoreCase(name)) {
                return book;
            }
        }
        return null;
    }

    // Helper method to find an issued book by its name
    private Book findIssuedBook(String name) {
        for (Book book : issuedBooks) {
            if (book.getBookName().equalsIgnoreCase(name)) {
                return book;
            }
        }
        return null;
    }

    public void issueBook(String bookName, String studentName) {
        Book book = findAvailableBook(bookName);

        if (book != null) {
            book.setIssuedTo(studentName);
            book.setIssuedOn(LocalDate.now());

            issuedBooks.add(book);
            allBooks.remove(book);
            System.out.println("\nBook issued successfully to " + studentName);
        } else if (findIssuedBook(bookName) != null) {
            System.out.println("\nBook is already issued to someone else.");
        } else {
            System.out.println("\nBook is not available in our library.");
        }
    }

    public void returnBook(String bookName) {
        Book book = findIssuedBook(bookName);

        if (book != null) {
            book.setIssuedTo(null);
            book.setIssuedOn(null);

            allBooks.add(book);
            issuedBooks.remove(book);
            System.out.println("\nBook returned successfully!");
        } else {
            System.out.println("\nThis book was not marked as issued.");
        }
    }

    public void showAllBooks() {
        if (allBooks.isEmpty()) {
            System.out.println("\nNo books currently available on shelves.");
        } else {
            System.out.println("\n--- Available Books ---");
            for (Book book : allBooks) {
                System.out.println("Title: " + book.getBookName() + " | Author: " + book.getAuthor());
            }
        }
    }

    public void showIssuedBooks() {
        if (issuedBooks.isEmpty()) {
            System.out.println("\nNo books are currently issued.");
        } else {
            System.out.println("\n--- Issued Books ---");
            for (Book book : issuedBooks) {
                System.out.println("Title: " + book.getBookName() +
                        " | Issued To: " + book.getIssuedTo() +
                        " | Date: " + book.getIssuedOn());
            }
        }
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- Library Management System -----");
            System.out.println(
                    "1. Add Book\n2. Issue Book\n3. Return Book\n4. Show Available Books\n5. Show Issued Books\n6. Exit");
            System.out.print("Your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Book name: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author name: ");
                    String author = sc.nextLine();

                    // Crucial: Create a NEW instance container for every new book added
                    library.addBook(new Book(title, author));
                }

                case 2 -> {
                    System.out.print("Enter book name to issue: ");
                    String issueTitle = sc.nextLine();
                    System.out.print("Enter student name: ");
                    String student = sc.nextLine();
                    library.issueBook(issueTitle, student);
                }

                case 3 -> {
                    System.out.print("Enter book name to return: ");
                    String returnTitle = sc.nextLine();
                    library.returnBook(returnTitle);
                }

                case 4 -> library.showAllBooks();

                case 5 -> library.showIssuedBooks();

                case 6 -> {
                    System.out.println("Thanks for using, Goodbye!");
                    sc.close();
                    return;
                }

                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}