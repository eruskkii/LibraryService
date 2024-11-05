package services.impl;

import collections.BookList;
import model.Books;
import model.Users.Librarian;
import model.Users.Users;
import services.LibraryService;

import java.util.LinkedList;
import java.util.Queue;



public class LibraryServiceImplFifo implements LibraryService {

    private Librarian librarian;
    Queue<Users> borrowingQueue = new LinkedList<>();

    public LibraryServiceImplFifo() {
        this.borrowingQueue = new LinkedList<>();
    }


    public void addUserToQueue(Users user) {
        borrowingQueue.add(user);

    }

    public void addBookToShelf(BookList bookList, Books newBook) {
        bookList.addBook(newBook);
        System.out.println("Book added successfully!");
    }

    public void removeBookFromShelf(BookList bookList, String bookTitle) {
        Books book = bookList.findBookByTitle(bookTitle);
        if (book != null) {
            bookList.removeBook(book.getId());
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found.");
        }


    }

    public void restockBook(BookList bookList, String bookTitle) {
        Books book = bookList.findBookByTitle(bookTitle);
        if (book != null) {
            book.incrementCopies();
            System.out.println("Restocked successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }

    public void giveOutBooks(BookList bookList) {
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
}