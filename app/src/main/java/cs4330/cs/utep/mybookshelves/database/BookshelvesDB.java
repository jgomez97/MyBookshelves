package cs4330.cs.utep.mybookshelves.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cs4330.cs.utep.mybookshelves.manager.Book;
import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.manager.BookshelfType;

/**
 * @author Jesus Gomez
 *
 * Class that handles the database of the app.
 *
 * Class: CS4330
 * Instructor: Dr. Cheon
 * Assignment: Final project
 * Date of last modification: 04/17/2019
 **/

public class BookshelvesDB extends SQLiteOpenHelper {

    /** Information */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookshelvesDB";
    private static final String BOOKSHELVES_TABLE_NAME = "Bookshelves";
    public static final String COL_BOOKSHELF_NAME = "Bookshelf_Name";
    public static final String COL_BOOKSHELF_DATE = "Bookshelf_Date";
    public static final String COL_BOOKSHELF_TYPE = "Bookshelf_Type";

    private static final String BOOKS_TABLE_NAME = "Books";
    public static final String COL_BOOK_TITLE = "Book_Title";
    public static final String COL_BOOK_AUTHOR = "Book_Author";
    public static final String COL_BOOK_BOOKSHELF = "Book_Bookshelf";
    public static final String COL_BOOK_NUMPAGES = "Book_NumPages";
    public static final String COL_BOOK_ISBN = "Book_Isbn";
    public static final String COL_BOOK_DATE = "Book_Date";
    public static final String COL_BOOK_IMGURL = "Book_Img";
    public static final String COL_BOOK_LANGUAGE = "Book_Language";
    public static final String COL_BOOK_PUBLISHED_DATE = "Book_Published_Date";
    public static final String COL_BOOK_RATING = "Book_Rating";

    /** Constructor */
    public BookshelvesDB(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE IF NOT EXISTS " + BOOKSHELVES_TABLE_NAME + "("
                + COL_BOOKSHELF_NAME + " STRING PRIMARY KEY NOT NULL, "
                + COL_BOOKSHELF_DATE + " INTEGER, "
                + COL_BOOKSHELF_TYPE + " INTEGER" + ")";
        db.execSQL(table);

        table = "CREATE TABLE IF NOT EXISTS " + BOOKS_TABLE_NAME + "("
                + COL_BOOK_TITLE + " STRING PRIMARY KEY NOT NULL, "
                + COL_BOOK_AUTHOR + " STRING, "
                + COL_BOOK_BOOKSHELF + " STRING, "
                + COL_BOOK_NUMPAGES + " INTEGER, "
                + COL_BOOK_ISBN + " BIGINTEGER, "
                + COL_BOOK_DATE + " INTEGER, "
                + COL_BOOK_IMGURL + " STRING, "
                + COL_BOOK_LANGUAGE + " STRING, "
                + COL_BOOK_PUBLISHED_DATE + " INTEGER, "
                + COL_BOOK_RATING + " DECIMAL " + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + BOOKSHELVES_TABLE_NAME);
        onCreate(database);

        database.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE_NAME);
        onCreate(database);
    }

    /**
     * Adds a bookshelf into the database.
     *
     * @param bookshelf the bookshelf that is being added.
     * */
    public void addBookshelf(Bookshelf bookshelf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKSHELF_NAME, bookshelf.getName());
        values.put(COL_BOOKSHELF_DATE, bookshelf.getDateCreated());
        values.put(COL_BOOKSHELF_TYPE, bookshelf.getType() == BookshelfType.COLLECTION ? 1 : 0);
        db.insert(BOOKSHELVES_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Adds a book into the database.
     *
     * @param book the book that is being added.
     * */
    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOK_TITLE, book.getTitle());
        values.put(COL_BOOK_AUTHOR, book.getAuthor());
        values.put(COL_BOOK_BOOKSHELF, book.getBookshelfName());
        values.put(COL_BOOK_NUMPAGES, book.getNumPages());
        values.put(COL_BOOK_ISBN, book.getIsbn());
        values.put(COL_BOOK_DATE, book.getDateAdded());
        values.put(COL_BOOK_IMGURL, book.getImgURL());
        values.put(COL_BOOK_LANGUAGE, book.getLanguage());
        values.put(COL_BOOK_PUBLISHED_DATE, book.getPublishedDate());
        values.put(COL_BOOK_RATING, book.getRating());
        db.insert(BOOKS_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Gets a list of all the bookshelves from the database.
     *
     * @return the list of all bookshelves in the database.
     * */
    public List<Bookshelf> getAllBookshelves() {
        List<Bookshelf> bookshelves = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + BOOKSHELVES_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(0);
                    long date = cursor.getLong(1);
                    BookshelfType type = (cursor.getInt(2) == 1 ? BookshelfType.COLLECTION : BookshelfType.WISHLIST);
                    Bookshelf bookshelf = new Bookshelf(name, date, type, this);
                    bookshelves.add(bookshelf);
                } while (cursor.moveToNext());
            }
        }
        return bookshelves;
    }

    /**
     * Gets all the books from a certain bookshelve that are in the database.
     *
     * @param  bookshelfName The name of the bookshelf we are looking for.
     * @return               the list of all books in the database.
     * */
    public List<Book> getAllBooks(String bookshelfName) {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + BOOKS_TABLE_NAME + " WHERE " + COL_BOOK_BOOKSHELF + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {bookshelfName});
        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(0);
                    String author = cursor.getString( 1);
                    String bookBookshelfName = cursor.getString(2);
                    int numPages = cursor.getInt(3);
                    long isbn = cursor.getLong(4);
                    long date = cursor.getLong(5);
                    String imgURL = cursor.getString(6);
                    String language = cursor.getString(7);
                    int publishedDate = cursor.getInt(8);
                    double rating = cursor.getDouble(9);

                    Book book = new Book(title, author, bookBookshelfName, numPages, isbn, date, imgURL, publishedDate, rating, language);
                    books.add(book);
                } while (cursor.moveToNext());
            }
        }
        return books;
    }

    /**
     * Deletes a bookshelf from the database.
     *
     * @param  bookshelfName The name of the bookshelf we are trying to delete.
     * */
    public void deleteBookshelf(String bookshelfName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKSHELVES_TABLE_NAME, COL_BOOKSHELF_NAME + " = ?", new String[] { bookshelfName } );
        db.close();
    }

    /**
     * Deletes a book from the database.
     *
     * @param  bookTitle The title of the book that we are trying to delete.
     * */
    public void deleteBook(String bookTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKS_TABLE_NAME, COL_BOOK_TITLE + " = ?", new String[] { bookTitle } );
        db.close();
    }

    /**
     * Updates a bookshelf that already exists on the database.
     *
     * @param  oldName   The name that the bookshelf used to have.
     * @param  bookshelf The bookshelf that we are are going to update.
     * */
    public void updateBookshelf(String oldName, Bookshelf bookshelf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKSHELF_NAME, bookshelf.getName());
        values.put(COL_BOOKSHELF_DATE, bookshelf.getDateCreated());
        values.put(COL_BOOKSHELF_TYPE, bookshelf.getType() == BookshelfType.COLLECTION ? 1 : 0);

        db.update(BOOKSHELVES_TABLE_NAME, values, COL_BOOKSHELF_NAME + " = ?", new String[]{oldName});
        db.close();
    }

    /**
     * Updates a book that already exists on the database.
     *
     * @param  oldTitle The title that the book used to have.
     * @param  book     The book that we are are going to update.
     * */
    public void updateBook(String oldTitle, Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOK_TITLE, book.getTitle());
        values.put(COL_BOOK_AUTHOR, book.getAuthor());
        values.put(COL_BOOK_BOOKSHELF, book.getBookshelfName());
        values.put(COL_BOOK_NUMPAGES, book.getNumPages());
        values.put(COL_BOOK_ISBN, book.getIsbn());
        values.put(COL_BOOK_DATE, book.getDateAdded());
        values.put(COL_BOOK_IMGURL, book.getImgURL());
        values.put(COL_BOOK_LANGUAGE, book.getLanguage());
        values.put(COL_BOOK_PUBLISHED_DATE, book.getPublishedDate());
        values.put(COL_BOOK_RATING, book.getRating());

        db.update(BOOKS_TABLE_NAME, values, COL_BOOK_TITLE + " = ?", new String[]{oldTitle});
        db.close();
    }

    /**
     * Transfers a book into another bookshelf.
     *
     * @param  bookTitle        The title of the book that is being transferred.
     * @param  newBookshelfName The name of the bookshelf that the book is being transferred into.
     * */
    public void transferBook(String bookTitle, String newBookshelfName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOK_BOOKSHELF, newBookshelfName);
        db.update(BOOKS_TABLE_NAME, values, COL_BOOK_TITLE + " = ?", new String[]{bookTitle});
        db.close();
    }
}