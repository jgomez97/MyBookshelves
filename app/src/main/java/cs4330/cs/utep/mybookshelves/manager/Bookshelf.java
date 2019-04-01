package cs4330.cs.utep.mybookshelves.manager;

import java.io.Serializable;
import java.util.HashMap;

public class Bookshelf implements Serializable {

    private String name;
    private long dateCreated;
    private BookshelfType type;

    /** Used in order to store books. */
    private static HashMap<String, Book> books = new HashMap<String, Book>();

    /** Constructor */
    public Bookshelf() {
    }

    public Bookshelf(String name, long dateCreated, BookshelfType type) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.type = type;
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
