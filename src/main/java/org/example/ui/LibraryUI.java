package org.example.ui;


import java.sql.SQLOutput;
import java.util.Optional;
import java.util.Scanner;

import enums.Role;
import enums.StudentLevel;
import model.Users.Librarian;
import model.Users.Student;
import model.Users.Teachers;
import model.Users.Users;
import model.Books;
import collections.BookList;
import services.impl.LibraryServiceImpl;
import services.impl.LibraryServiceImplFifo;
import collections.UserList;

public class LibraryUI {
    private static Scanner scanner = new Scanner(System.in);

    private static LibraryServiceImpl libraryService;

    private static UserList userList;

    private static LibraryServiceImplFifo fifoService;

    private static BookList bookList;

    private static Librarian librarian;






//    public static void main(String[] args) {
//        initializeLibrary();
//        showMainMenu();
//    }

    public static void runLibrary() {
        startLibrary();      // Initialize books and librarian
        displayMainMenu();   // Show the main menu
    }


    private static void startLibrary() {
        bookList = new BookList();

        bookList.addBook(new Books(1, "The Count of Monte Cristo", 5,"Dumas Pere"));
        bookList.addBook(new Books(2, "The Three Musketeers", 3,"Dumas Pere"));
        bookList.addBook(new Books(3, "Purple Hibiscus", 7,"Chimamanda Ngozi Adichie"));
        bookList.addBook(new Books(4, "Oliver Twist", 4,"Charles Dickens"));
        bookList.addBook(new Books(5, "The Godfather", 1,"Mario Puzo"));

//        Create default librarian

        librarian = new Librarian("Alice");
        libraryService = new LibraryServiceImpl(librarian);
        fifoService = new LibraryServiceImplFifo();
        userList = new UserList();

        userList.addUser(new Teachers("Paul", 2001, Role.TEACHER));

        userList.addUser(new Student("Emmanuel", 2002, StudentLevel.SENIOR));

        userList.addUser(new Student("Peski", 2003, StudentLevel.JUNIOR));
    }

    private static void displayMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("Welcome to the Nigerian Online Library");
            System.out.println("How would you like to login? Choose from 1 - 4 to login");
            System.out.println("1. Librarian");
            System.out.println("2. Student");
            System.out.println("3. Teacher");
            System.out.println("4. Exit");

            int role = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (role) {
                case 1:
                    LibrarianMenu();
                    break;
                case 2:
                    studentOrTeacherMenu("Student");
                    break;
                case 3:
                    studentOrTeacherMenu("Teacher");
                    break;
                case 4:
                    running = false;  // Exit the whole program
                    System.out.println("Thank you for using the Nigerian Online Library. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid selection. Please pick a number from 1-4.");
            }
        }
    }



    private static void LibrarianMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Welcome Librarian " + librarian.getName());
            System.out.println("What would you like to do?");
            System.out.println("1. Add a book to shelf");
            System.out.println("2. Remove a book from shelf");
            System.out.println("3. Give out books to those on queue - either FIFO or Priority");
            System.out.println("4. Restock books");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter book author:");
                    String author = scanner.nextLine();
                    System.out.println("How many copies of the book do you have?");
                    int copies = scanner.nextInt();
                    scanner.nextLine();
                    Books newBook = new Books(bookList.getNextId(), title, copies, author);
                    libraryService.addBookToShelf(bookList, newBook);
                    break;

                case 2:
                    System.out.println("Enter the title of the book to remove:");
                    title = scanner.nextLine();
                    libraryService.removeBookFromShelf(bookList, title);
                    break;

                case 3:
                    System.out.println("Select giving out method: 1. FIFO  2. Priority");
                    int methodChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    if (methodChoice == 1) {
                        System.out.println("Giving out books based on FIFO...");
                        fifoService.giveOutBooks(bookList);
                    } else if (methodChoice == 2) {
                        System.out.println("Giving out books based on Priority...");
                        libraryService.giveOutBooks(bookList);
                    } else {
                        System.out.println("Invalid choice. Please select either 1 or 2.");
                    }
                    break;

                case 4:
                    System.out.println("Enter the title of the book to restock:");
                    title = scanner.nextLine();
                    Books reBook = bookList.findBookByTitle(title);
                    if (reBook != null) {
                        System.out.println("Enter the number of copies to add:");
                        copies = scanner.nextInt();
                        scanner.nextLine(); // Consume newline left-over
                        libraryService.restockBook(bookList, title, copies);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting Librarian Menu...");
                    displayMainMenu();
                    break;

                default:
                    System.out.println("Invalid choice. Please select between 1-5.");
            }
        }
    }

    private static void studentOrTeacherMenu(String role) {
        System.out.println("Welcome " + role + "!");
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();

        boolean running = true;

        while (running) {
            System.out.println("What would you like to do?");
            System.out.println("1. Borrow a book");
            System.out.println("2. Return a book");
            System.out.println("3. View available books");
            System.out.println("4. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    borrowBookFlow(name, role);  // Borrow a book
                    break;
                case 2:
                    returnBookFlow(name, role);  // Return a book
                    break;
                case 3:
                    bookList.listAllBooks();  // List all books available in the library
                    break;
                case 4:
                    running = false;  // Exit the student/teacher menu to go back to the main menu
                    break;
                default:
                    System.out.println("Invalid option. Please choose from 1-4.");
            }
        }

        // Go back to the main menu after the loop ends
        displayMainMenu();
    }

    private static void borrowBookFlow(String name, String role) {
        System.out.println("Enter the title of the book you want to borrow:");
        String bookTitle = scanner.nextLine();

        Optional<Books> requestedBook = Optional.ofNullable(bookList.findBookByTitle(bookTitle));

        requestedBook.ifPresentOrElse(
                book -> {
                    if (book.getCopies() > 0) {
                        int userId = userList.getNextUserId();


                        Users user = Optional.of(role)
                                .filter(r -> r.equalsIgnoreCase("Student"))
                                .<Users>map(r -> {
                                    System.out.println("Enter student level (Junior/Senior):");
                                    String levelInput = scanner.nextLine();
                                    StudentLevel level = levelInput.equalsIgnoreCase("Senior") ? StudentLevel.SENIOR : StudentLevel.JUNIOR;
                                    return new Student(name, userId, level);
                                })
                                .orElse(new Teachers(name, userId, Role.TEACHER));

                        // Add user to the UserList to maintain records
                        Optional.ofNullable(user).ifPresent(userList::addUser);

                        // Set the requested book title and add to borrowing queue
                        user.setRequestedBookTitle(bookTitle);
                        libraryService.addUserToQueue(user);

                        System.out.println(role + " " + name + " has requested the book: " + bookTitle + " and is now in the borrowing queue.");
                    } else {
                        System.out.println("The book is currently not available.");
                    }
                },
                () -> System.out.println("The book '" + bookTitle + "' does not exist in the library.")
        );
    }





    private static void returnBookFlow(String name, String role) {
        System.out.println("Enter the title of the book you want to return:");
        String bookTitle = scanner.nextLine();

        Optional<Books> returnedBook = Optional.ofNullable(bookList.findBookByTitle(bookTitle));

        returnedBook.ifPresentOrElse(
                book -> {
                    book.incrementCopies(); // Assume returning one copy at a time
                    System.out.println("The book '" + bookTitle + "' has been returned successfully by " + role + " " + name);
                },
                () -> System.out.println("The book '" + bookTitle + "' does not exist in the library.")
        );
    }


}







