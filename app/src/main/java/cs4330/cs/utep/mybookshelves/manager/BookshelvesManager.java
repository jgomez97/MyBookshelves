package cs4330.cs.utep.mybookshelves.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs4330.cs.utep.mybookshelves.database.BookshelvesDB;

public class BookshelvesManager implements Serializable {

    /** Used in order to store books. */
    private static HashMap<String, Bookshelf> bookshelves = new HashMap<String, Bookshelf>();

    public static BookshelvesDB db;

    /** Default Constructor*/
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
        if(oldName.equalsIgnoreCase("new")) {
            bookshelves.put(bookshelf.getName(), bookshelf);
            db.addBookshelf(bookshelf);
        } else {
            updateBookshelf(oldName, bookshelf);
        }
    }

    private void updateBookshelf(String oldName, Bookshelf bookshelf) {
        bookshelves.remove(oldName);
        bookshelves.put(bookshelf.getName(), bookshelf);
        db.updateBookshelf(oldName, bookshelf);
    }

    public boolean bookshelfExists(String bookshelfName) {
        return bookshelves.containsKey(bookshelfName);
    }

    public boolean isEmpty() {
        return bookshelves.isEmpty();
    }
}