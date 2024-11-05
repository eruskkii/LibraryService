package model;

public class Books {

    private int bookId;

    private String bookTitle;

    private int copies;

    private String author;


    public Books() {
    }

    public Books(int bookId, String bookTitle, int copies, String author) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.copies = copies;
        this.author = author;
    }

    public int getId() {
        return bookId;
    }



    public String getBookTitle() {
        return bookTitle;
    }

    public int getCopies() {
        return copies;
    }

    public void incrementCopies() {this.copies++;
    }

    public void incrementCopies(int copies) {
        if (copies > 0) {
            this.copies += copies;
        }
    }

    public void decrementCopies() {
        if (this.copies > 0) {
            this.copies--;
        }
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", quantity=" + copies +
                ", author='" + author + '\'' +
                '}';
    }
}
