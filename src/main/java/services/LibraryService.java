package services;

import collections.BookList;
import model.Books;
import model.Users.Users;

public interface LibraryService {

    void borrowBook(BookList bookList, Books book, Users user);
    void returnBook(BookList bookList, Books book, Users user);
}
