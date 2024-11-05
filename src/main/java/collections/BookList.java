package collections;

import model.Books;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BookList {

    private Map<Integer, Books> bookShelf = new HashMap<>();


    public int getNextId() {
        int highestId = 0;
        for (int id : bookShelf.keySet()) {
            if (id > highestId) {
                highestId = id;  // Update highestId if the current key is greater
            }
        }
        return highestId + 1;  // Return the next available ID
    }


    public void addBook(Books book){
        if (book != null) {
            bookShelf.put(book.getId(), book);
        }
    }

    public void removeBook(int bookId) {
        if (bookShelf.containsKey(bookId)) {
            bookShelf.remove(bookId);
            System.out.println("The book with the title " + bookId + " has been removed");
        } else {
            System.out.println("The book with the title '" + bookId + "' does not exist in the collection.");
        }
    }

    public Books findBook(int bookId) {
        if (bookShelf.containsKey(bookId)) {
            return bookShelf.get(bookId);
        }
        return null;
    }

    public Books findBookByTitle(String bookTitle) {
        return bookShelf.values().stream()
                .filter(book -> book.getBookTitle().equalsIgnoreCase(bookTitle))
                .findFirst()
                .orElse(null); // Return null if not found
    }

    public void listAllBooks() {
        if (bookShelf.isEmpty()) {
            System.out.println("No books are currently available.");
        } else {
            System.out.println("Books available in the library:");

            // Using forEach to iterate over the HashMap entries
            bookShelf.forEach((bookId, book) ->
                    System.out.println("- ID: " + bookId + ", Title: " + book.getBookTitle()
                            + ", Author: " + book.getAuthor() + ", Copies Available: " + book.getCopies())
            );
        }
//        Previous code
//            for (int bookId : bookShelf.keySet()) {
//                // Get the book object associated with the current bookId
//                Books book = bookShelf.get(bookId);
//                // Print book details
//                System.out.println("- ID: " + bookId + ", Title: " + book.getBookTitle() + ", Author: " + book.getAuthor() + ", Copies Available: " + book.getCopies());
//
//
//            }
        }
}

