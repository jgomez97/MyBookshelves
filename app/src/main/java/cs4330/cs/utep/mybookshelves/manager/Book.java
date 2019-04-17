package cs4330.cs.utep.mybookshelves.manager;

import java.io.Serializable;

/**
 * @author Jesus Gomez
 *
 * Class that stores all the information
 * of a book.
 *
 * Class: CS4330
 * Instructor: Dr. Cheon
 * Assignment: Final project
 * Date of last modification: 04/17/2019
 **/

public class Book implements Serializable {

    /** Variables */
    private String title, author, bookshelfName, imgURL, language;
    private int numPages, publishedDate;
    private double rating;
    private long isbn, dateAdded;

    /** Constructors */
    public Book() {}

    public Book(String title, String author, String bookshelfName, int numPages,
                long isbn, long dateAdded, String imgURL, int publishedDate,
                double rating, String language) {
        this.title = title;
        this.author = author;
        this.bookshelfName = bookshelfName;
        this.numPages = numPages;
        this.isbn = isbn;
        this.dateAdded = dateAdded;
        this.imgURL = imgURL;
        this.publishedDate = publishedDate;
        this.rating = rating;
        this.language = language;
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

    public String getImgURL() {
        return imgURL;
    }

    public int getPublishedDate() {
        return  publishedDate;
    }

    public double getRating() {
        return rating;
    }

    public String getLanguage() {
        return language;
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

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setPublishedDate(int publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
