package cs4330.cs.utep.mybookshelves.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs4330.cs.utep.mybookshelves.database.BookshelvesDB;

/**
 * @author Jesus Gomez
 *
 * Class that stores all the bookshelves
 * and handles them.
 *
 * Class: CS4330
 * Instructor: Dr. Cheon
 * Assignment: Final project
 * Date of last modification: 04/17/2019
 **/

public class BookshelvesManager implements Serializable {

    /** Used in order to store books. */
    private static HashMap<String, Bookshelf> bookshelves = new HashMap<String, Bookshelf>();

    /** Database */
    public static BookshelvesDB db;

    /**Constructors*/
    public BookshelvesManager(Context context) {
        db = new BookshelvesDB(context);

        SQLiteDatabase bookshelvesDB = context.openOrCreateDatabase("BookshelvesDB", context.MODE_PRIVATE,null);

        db.onCreate(bookshelvesDB);

        List<Bookshelf> bookshelves = db.getAllBookshelves();
        for(Bookshelf bookshelf : bookshelves) {
            List<Book> books = db.getAllBooks(bookshelf.getName());
            for(Book book : books)
                bookshelf.addBook(book.getTitle(), book);
            this.bookshelves.put(bookshelf.getName(), bookshelf);
        }
    }

    /**
     * Generates a list of bookshelves by iterating trough the hashmap.
     * Necessary for the list views and context menus.
     *
     * @return the list of all bookshelves stored in the hashmap.
     * */
    public List<Bookshelf> getBookShelvesInArray() {
        ArrayList<Bookshelf> bookshelvesList = new ArrayList<Bookshelf>();
        for(Bookshelf bookshelf : bookshelves.values()) {
            bookshelvesList.add(bookshelf);
        }
        return bookshelvesList;
    }

    /**
     * Gets a bookshelf that is stored in the hashmap with the given name.
     *
     * @param bookshelfName the name of the bookshelf that we want.
     * @return              the bookshelf that is stored on the hashmap.
     * */
    public Bookshelf getBookshelf(String bookshelfName) {
        return bookshelves.get(bookshelfName);
    }

    /**
     * Checks if the hashmap contains a bookshelf with the given name.
     *
     * @param bookshelfName the name of the bookshelf that we want to look for.
     * @return          true if bookshelf already exists, false otherwise.
     * */
    public boolean bookshelfExists(String bookshelfName) {
        return bookshelves.containsKey(bookshelfName);
    }

    /**
     * Tries to add a bookshelf into the hashmap, if the bookshelf is not new, it will just update its entry.
     *
     * @param oldName    name that it used to have, "new" if its the first time being added.
     * @param bookshelf  bookshelf that is going to be stored.
     * */
    public void addBookshelf(String oldName, Bookshelf bookshelf) {
        if(oldName.equalsIgnoreCase("new")) {
            bookshelves.put(bookshelf.getName(), bookshelf);
            db.addBookshelf(bookshelf);
        } else {
            updateBookshelf(oldName, bookshelf);
        }
    }

    /**
     * Deletes a bookshelf that is in the hashmap with the given name.
     *
     * @param bookshelfName the name of the bookshelf that we want to delete.
     * */
    public void deleteBookshelf(String bookshelfName) {
        List<Book> books = db.getAllBooks(bookshelfName);
        for(Book book : books)
            db.deleteBook(book.getTitle());
        bookshelves.remove(bookshelfName);
        db.deleteBookshelf(bookshelfName);
    }

    /**
     * Updates the information of certain bookshelf that is in the hashmap.
     *
     * @param oldName    the name that it used to have.
     * @param bookshelf  the bookshelf that we are going to replace.
     * */
    private void updateBookshelf(String oldName, Bookshelf bookshelf) {
        List<Book> books = db.getAllBooks(oldName);
        for(Book book : books)
            db.transferBook(book.getTitle(), bookshelf.getName());
        bookshelves.remove(oldName);
        bookshelves.put(bookshelf.getName(), bookshelf);
        db.updateBookshelf(oldName, bookshelf);
    }

    /**
     * Checks if the hashmap is empty, used by the title component in MainActivity.
     * @see cs4330.cs.utep.mybookshelves.MainActivity
     *
     * @return  true if is empty, false otherwise.
     * */
    public boolean isEmpty() {
        return bookshelves.isEmpty();
    }
}