package cs4330.cs.utep.mybookshelves.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookshelvesManager implements Serializable {

    /** Used in order to store books. */
    private static HashMap<String, Bookshelf> bookshelves = new HashMap<String, Bookshelf>();

    public Bookshelf getBookshelf(String bookshelfName) {
        return bookshelves.get(bookshelfName);
    }

    public List<Bookshelf> getBookShelvesInArray() {
        ArrayList<Bookshelf> bookshelvesList = new ArrayList<Bookshelf>();
        for(Bookshelf bookshelf : bookshelves.values()) {
            bookshelvesList.add(bookshelf);
        }
        return bookshelvesList;
    }

    public void addBookshelf(String oldName, Bookshelf bookshelf) {
        if(oldName.equalsIgnoreCase("new"))
            bookshelves.put(bookshelf.getName(), bookshelf);
        else
            updateBookshelf(oldName, bookshelf);
    }

    private void updateBookshelf(String oldName, Bookshelf bookshelf) {
        bookshelves.remove(oldName);
        bookshelves.put(bookshelf.getName(), bookshelf);
    }

    public boolean bookshelfExists(String bookshelfName) {
        return bookshelves.containsKey(bookshelfName);
    }

    public boolean isEmpty() {
        return bookshelves.isEmpty();
    }
}