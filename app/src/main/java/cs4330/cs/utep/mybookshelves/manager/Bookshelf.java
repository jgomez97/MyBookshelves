package cs4330.cs.utep.mybookshelves.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bookshelf implements Serializable {

    private String name;
    private long dateCreated;
    private BookshelfType type;

    /** Used in order to store books. */
    private static HashMap<String, Book> books = new HashMap<String, Book>();


    public List<Book> getBooksInArray() {
        ArrayList<Book> list = new ArrayList<Book>();
        for(Book book : books.values()) {
            list.add(book);
        }
        return list;
    }

    public Book getBook(String bookTitle) {
        return books.get(bookTitle);
    }

    public boolean bookExists(String bookTitle) {
        return books.containsKey(bookTitle);
    }

    public void addBook(String oldTitle, Book book) {
        if(oldTitle.equalsIgnoreCase("new"))
            books.put(book.getTitle(), book);
        else
            updateBook(oldTitle, book);
    }

    private void updateBook(String oldName, Book book) {
        books.remove(oldName);
        books.put(book.getTitle(), book);
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }


    public int getNumBooks() {
        return books.size();
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
