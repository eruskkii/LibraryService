package model.Users;

import collections.BookList;
import model.Books;

public class Librarian {

    private String name;

    private static Books book;

   public Librarian(){

   }

    public Librarian(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addBook(BookList bookList, Books book) {
        if (book != null) {
            bookList.addBook(book);
            System.out.println("Librarian " + name + " added the book: " + book.getBookTitle());
        } else {
            System.out.println("Cannot add a null book.");
        }
    }



    public void removeBook(BookList bookList, int bookId) {
        Books book = bookList.findBook(bookId);  // Get the book directly
        if (book != null) { // Check if the book is found
            bookList.removeBook(bookId);  // Remove the book from the collection
            System.out.println("Librarian " + name + " removed the book: " + book.getBookTitle());
        } else {
            System.out.println("The book with ID '" + bookId + "' is not available in the library.");
        }
    }


    }


