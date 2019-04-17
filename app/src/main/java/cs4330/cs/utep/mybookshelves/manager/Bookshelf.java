package cs4330.cs.utep.mybookshelves.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs4330.cs.utep.mybookshelves.database.BookshelvesDB;

/**
 * @author Jesus Gomez
 *
 * Class that stores all the information
 * of a bookshelf and handles them.
 *
 * Class: CS4330
 * Instructor: Dr. Cheon
 * Assignment: Final project
 * Date of last modification: 04/17/2019
 **/

public class Bookshelf implements Serializable {


    /** Variables */
    private String name;
    private long dateCreated;
    private BookshelfType type;

    /** Used in order to store books. */
    private HashMap<String, Book> books = new HashMap<String, Book>();

    /** SQL Database */
    private static BookshelvesDB db;

    /** Constructors */
    public Bookshelf(BookshelvesDB db) {
        this.db = db;
    }

    public Bookshelf(String name, long dateCreated, BookshelfType type, BookshelvesDB db) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.type = type;
        this.db = db;
    }

    /**
     * Generates a list of books by iterating trough the hashmap.
     * Necessary for the list views and context menus.
     *
     * @return the list of all books stored in the hashmap.
     * */
    public List<Book> getBooksInArray() {
        ArrayList<Book> list = new ArrayList<Book>();
        for(Book book : books.values()) {
            list.add(book);
        }
        return list;
    }

    /**
     * Gets a book that is stored in the hashmap with the given title.
     *
     * @param bookTitle the title of the book that we want.
     * @return          the book that is stored on the hashmap.
     * */
    public Book getBook(String bookTitle) {
        return books.get(bookTitle);
    }

    /**
     * Checks if the hashmap contains a book with the given title.
     *
     * @param bookTitle the title of the book that we want to look for.
     * @return          true if book already exists, false otherwise.
     * */
    public boolean bookExists(String bookTitle) {
        return books.containsKey(bookTitle);
    }

    /**
     * Tries to add a book into the hashmap, if the book is not new, it will just update its entry.
     *
     * @param oldTitle tile that it used to have, "new" if its the first time being added.
     * @param book     book that is going to be stored.
     * */
    public void addBook(String oldTitle, Book book) {
        if(oldTitle.equalsIgnoreCase("new")) {
            books.put(book.getTitle(), book);
            db.addBook(book);
        } else {
            updateBook(oldTitle, book);
        }
    }

    /**
     * Deletes a book that is in the hashmap with the given title.
     *
     * @param bookTitle the title of the book that we want to delete.
     * */
    public void deleteBook(String bookTitle){
        books.remove(bookTitle);
        db.deleteBook(bookTitle);
    }

    /**
     * Updates the information of certain book that is in the hashmap.
     *
     * @param oldTitle the title that it used to have.
     * @param book     the book that we are going to replace.
     * */
    private void updateBook(String oldTitle, Book book) {
        books.remove(oldTitle);
        books.put(book.getTitle(), book);
        db.updateBook(oldTitle, book);
    }

    /** Getters */
    public String getName() {
        return name;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public BookshelfType getType() {
        return type;
    }

    public int getNumBooks() {
        return books.size();
    }

    /** Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setType(BookshelfType type) {
        this.type = type;
    }
}
