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
    public static final String COL_BOOK_PRICE = "Book_Price";

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
                + COL_BOOK_PRICE + " DECIMAL " + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + BOOKSHELVES_TABLE_NAME);
        onCreate(database);

        database.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE_NAME);
        onCreate(database);
    }

    public void addBookshelf(Bookshelf bookshelf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKSHELF_NAME, bookshelf.getName());
        values.put(COL_BOOKSHELF_DATE, bookshelf.getDateCreated());
        values.put(COL_BOOKSHELF_TYPE, bookshelf.getType() == BookshelfType.COLLECTION ? 1 : 0);
        db.insert(BOOKSHELVES_TABLE_NAME, null, values);
        db.close();
    }

    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOK_TITLE, book.getTitle());
        values.put(COL_BOOK_AUTHOR, book.getAuthor());
        values.put(COL_BOOK_BOOKSHELF, book.getBookshelfName());
        values.put(COL_BOOK_NUMPAGES, book.getNumPages());
        values.put(COL_BOOK_ISBN, book.getIsbn());
        values.put(COL_BOOK_DATE, book.getDateAdded());
        values.put(COL_BOOK_PRICE, book.getPrice());
        db.insert(BOOKS_TABLE_NAME, null, values);
        db.close();
    }

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
                    double price = cursor.getDouble(6);

                    Book book = new Book(title, author, bookBookshelfName, numPages, isbn, date, price);
                    books.add(book);
                } while (cursor.moveToNext());
            }
        }
        return books;
    }

    /**
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, new String[]{});
        db.close();
    }

    public void delete(String bookshelfName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_BOOKSHELF_NAME + " = ?", new String[] { bookshelfName } );
        db.close();
    }*/

    public void updateBookshelf(String oldName, Bookshelf bookshelf) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOKSHELF_NAME, bookshelf.getName());
        values.put(COL_BOOKSHELF_DATE, bookshelf.getDateCreated());
        values.put(COL_BOOKSHELF_TYPE, bookshelf.getType() == BookshelfType.COLLECTION ? 1 : 0);

        db.update(BOOKSHELVES_TABLE_NAME, values, COL_BOOKSHELF_NAME + " = ?", new String[]{oldName});
        db.close();
    }

    public void updateBook(String oldName, Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_BOOK_TITLE, book.getTitle());
        values.put(COL_BOOK_AUTHOR, book.getAuthor());
        values.put(COL_BOOK_BOOKSHELF, book.getBookshelfName());
        values.put(COL_BOOK_NUMPAGES, book.getNumPages());
        values.put(COL_BOOK_ISBN, book.getIsbn());
        values.put(COL_BOOK_DATE, book.getDateAdded());
        values.put(COL_BOOK_PRICE, book.getPrice());

        db.update(BOOKS_TABLE_NAME, values, COL_BOOK_TITLE + " = ?", new String[]{oldName});
        db.close();
    }

}
