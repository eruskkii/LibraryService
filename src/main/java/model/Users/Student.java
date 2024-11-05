package model.Users;

import enums.Role;
import enums.StudentLevel;

import java.awt.print.Book;
import java.util.List;

public class Student extends Users{



    private List<Book> borrowedBooks;

    private int borrowLimit;

    private StudentLevel level;


    public Student(String name, int userId, StudentLevel level) {
        super(name, userId);
        this.level = level;
        this.role = Role.STUDENT;
    }

    public StudentLevel getLevel() {
        return level;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(int borrowLimit) {
        this.borrowLimit = borrowLimit;
    }
}
