package cs4330.cs.utep.mybookshelves.manager;

import java.io.Serializable;

public class Book implements Serializable {

    private String title, author, bookshelfName;
    private int numPages;
    private long isbn, dateAdded;
    private double price;

    /** Default Constructor */
    public Book() {

    }

    public Book(String title, String author, String bookshelfName, int numPages,
                long isbn, long dateAdded, double price) {
        this.title = title;
        this.author = author;
        this.bookshelfName = bookshelfName;
        this.numPages = numPages;
        this.isbn = isbn;
        this.dateAdded = dateAdded;
        this.price = price;
    }

    /** Getters */

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookshelfName() {
        return bookshelfName;
    }

    public int getNumPages() {
        return numPages;
    }

    public long getIsbn() {
        return isbn;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public double getPrice() {
        return price;
    }

    /** Setters */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookshelfName(String bookshelfName) {
        this.bookshelfName = bookshelfName;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
