package cs4330.cs.utep.mybookshelves;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs4330.cs.utep.mybookshelves.manager.Book;
import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.manager.BookshelfType;
import cs4330.cs.utep.mybookshelves.manager.BookshelvesManager;

public class BookEditor extends AppCompatActivity {

    private Bookshelf bookshelf;
    private Book book;

    /** TextViews */
    private TextView title, titleEditing, authorEditing, pagesEditing, isbnEditing, priceEditing;

    /** Button */
    private Button saveBtn;

    private String bookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_editor);

        setUpComponents();

        Intent i = getIntent();
        bookshelf = (Bookshelf) i.getSerializableExtra("bookshelf");
        bookTitle = i.getStringExtra("bookName");
        if(bookTitle.equalsIgnoreCase("new")) {
            book = new Book();
        } else {
            book = bookshelf.getBook(bookTitle);
            titleEditing.setText(book.getTitle());
            authorEditing.setText(book.getAuthor());
            pagesEditing.setText(book.getNumPages());
            isbnEditing.setText("" + book.getIsbn());
            priceEditing.setText("" + book.getPrice());
            title.setText("Edit Book");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!titleEditing.getText().toString().matches("")) {
                    if(!authorEditing.getText().toString().matches("")) {
                        if(!pagesEditing.getText().toString().matches("")) {
                            if(!isbnEditing.getText().toString().matches("")) {
                                if(!priceEditing.getText().toString().matches("")) {
                                    if (!bookshelf.bookExists(titleEditing.getText().toString()) || (title.getText().toString().equalsIgnoreCase("Edit Book") && book.getTitle().equals(titleEditing.getText().toString()))) {
                                        saveBook();
                                        getIntent().putExtra("bookshelf", bookshelf);
                                        setResult(Activity.RESULT_OK, getIntent());
                                        finish();
                                    } else {
                                        titleEditing.setError("That title is already taken by other book.");
                                    }
                                } else {
                                    priceEditing.setError("The price field cannot be empty.");
                                }
                            } else {
                                isbnEditing.setError("The isbn field cannot be empty.");
                            }
                        } else {
                            pagesEditing.setError("The number of pages field cannot be empty.");
                        }
                    } else {
                        authorEditing.setError("The author field cannot be empty.");
                    }
                } else {
                    titleEditing.setError("The title field cannot be empty.");
                }
            }
        });
    }

    private void saveBook() {
        book.setTitle(titleEditing.getText().toString());
        book.setAuthor(authorEditing.getText().toString());
        book.setNumPages(Integer.parseInt(pagesEditing.getText().toString()));
        book.setIsbn(Long.parseLong(isbnEditing.getText().toString()));
        book.setPrice(Double.parseDouble(priceEditing.getText().toString()));
        book.setDateAdded(System.currentTimeMillis() / 1000);

        bookshelf.addBook(bookTitle, book);
    }

    /**
     * Defines all components used by the activity.
     * */
    private void setUpComponents() {
        title = findViewById(R.id.bookEditorTitle);
        titleEditing = findViewById(R.id.bookTitleEditing);
        authorEditing = findViewById(R.id.bookAuthorEditing);
        pagesEditing = findViewById(R.id.bookNumberPagesEditing);
        isbnEditing = findViewById(R.id.bookIsbnEditing);
        priceEditing = findViewById(R.id.bookPriceEditing);

        saveBtn = findViewById(R.id.bookSaveBtn);
    }
}
