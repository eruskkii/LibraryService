package services.impl;

import collections.BookList;
import model.Books;
import model.Users.Librarian;
import model.Users.Users;
import services.LibraryService;
import utilities.UserComparator;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class LibraryServiceImpl implements LibraryService {

    private Librarian librarian;
    private Queue<Users> borrowingQueue = new PriorityQueue<>();


    public LibraryServiceImpl(Librarian librarian) {
        borrowingQueue = new PriorityQueue<>(new UserComparator());
        this.librarian = librarian;
    }


    public void addUserToQueue(Users user) {
        borrowingQueue.add(user);

    }

    public void addBookToShelf(BookList bookList, Books newBook) {
        bookList.addBook(newBook);
        System.out.println("Book added successfully!");


    }

    public void removeBookFromShelf(BookList bookList, String bookTitle) {
        Optional.ofNullable(bookList.findBookByTitle(bookTitle))
                .ifPresentOrElse(
                        book -> {
                            bookList.removeBook(book.getId());
                            System.out.println("Book removed successfully!");
                        },
                        () -> System.out.println("Book not found.")
                );
    }

    public void restockBook(BookList bookList, String bookTitle, int copies) {
        Optional.ofNullable(bookList.findBookByTitle(bookTitle))
                .ifPresentOrElse(
                        book -> {
                            book.incrementCopies(copies); // Increment the number of copies
                            System.out.println("Restocked successfully with " + copies + " copies!");
                        },
                        () -> System.out.println("Book not found.")
                );
    }


// impreative method
//    public void removeBookFromShelf(BookList bookList, String bookTitle) {
//        Books book = bookList.findBookByTitle(bookTitle);
//        if (book != null) {
//            bookList.removeBook(book.getId());
//            System.out.println("Book removed successfully!");
//        } else {
//            System.out.println("Book not found.");
//        }
//    }


////    Imperative method
//
//    public void restockBook(BookList bookList, String bookTitle, int copies) {
//        Books book = bookList.findBookByTitle(bookTitle);
//        if (book != null) {
//            book.incrementCopies(copies);
//            System.out.println("Restocked successfully!");
//        } else {
//            System.out.println("Book not found.");
//        }
//    }

    public void giveOutBooks(BookList bookList) {

        displayQueue();
        // This can work for both FIFO and Priority since they involve processing users in a queue.
        Users nextUser = borrowingQueue.poll();
        if (nextUser != null) {
            Books book = bookList.findBookByTitle(nextUser.getRequestedBookTitle());
            if (book != null && book.getCopies() > 0) {
                bookList.removeBook(book.getId());
                book.decrementCopies();
                System.out.println("Librarian " + librarian.getName() +
                        " lent the book '" + book.getBookTitle() + "' to " + nextUser.getName());
            } else {
                System.out.println("The book '" + nextUser.getRequestedBookTitle() + "' is not available.");
            }
        } else {
            System.out.println("No users in queue for borrowing.");
        }
    }


    @Override
    public void borrowBook(BookList bookList, Books book, Users user) {


        if (book != null && book.getCopies() > 0) {
            // Add the user to the borrowing queue
            borrowingQueue.add(user);


            Users nextUser = borrowingQueue.poll();
            if (nextUser != null) {
                bookList.removeBook(book.getId());
                book.decrementCopies();
                System.out.println("Librarian " + librarian.getName() +
                        " lent the book '" + book.getBookTitle() + "' to " + nextUser.getName());
            }
        } else {
            System.out.println("The book '" + book.getBookTitle() +
                    "' is not available for borrowing.");
        }

    }

    @Override
    public void returnBook(BookList bookList, Books book, Users user) {
        if (book != null) {
            // Increment the number of copies available in the library
            book.incrementCopies();
            System.out.println(user.getName() + " has returned the book: " + book.getBookTitle());

            // Optionally, you can maintain a list of borrowed books per user to keep track
        } else {
            System.out.println("Error: The book to return does not exist in the library.");
        }
    }

//    public void displayQueue() {
//        if (borrowingQueue.isEmpty()) {
//            System.out.println("No users are currently in the borrowing queue.");
//        } else {
//            System.out.println("Current Borrowing Queue:");
//            int position = 1;
//
//
//            for (Users user : borrowingQueue) {
//                System.out.println(position + ". Name: " + user.getName() +
//                        ", Role: " + user.getRole() +
//                        ", Requested Book: " + user.getRequestedBookTitle());
//                position++;
//            }
//        }
//    }

    public void displayQueue() {
        if (borrowingQueue.isEmpty()) {
            System.out.println("No users are currently in the borrowing queue.");
        } else {
            System.out.println("Current Borrowing Queue:");
            AtomicInteger position = new AtomicInteger(1);
            borrowingQueue.forEach(user -> {
                System.out.println(position.getAndIncrement() + ". Name: " + user.getName() +
                        ", Role: " + user.getRole() +
                        ", Requested Book: " + user.getRequestedBookTitle());
            });
        }
    }

}


