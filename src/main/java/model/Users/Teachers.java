package model.Users;

import enums.Role;

import java.awt.print.Book;
import java.util.List;

public class Teachers extends Users{

    private List<Book> borrowedBooks;


    public Teachers(String name, int userId, Role role) {
        super(name, userId, role);
    }


    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }



}
