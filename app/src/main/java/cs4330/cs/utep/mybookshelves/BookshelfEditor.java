package cs4330.cs.utep.mybookshelves;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.manager.BookshelfType;
import cs4330.cs.utep.mybookshelves.manager.BookshelvesManager;

public class BookshelfEditor extends AppCompatActivity {

    /** Managers */
    private BookshelvesManager manager;
    private Bookshelf bookshelf;

    /** TextViews */
    private TextView title, nameEditing;

    /** Radio Group */
    private RadioGroup radioGroup;

    /** Radio Buttons */
    private RadioButton wishlist, collection;

    /** Button */
    private Button saveBtn;

    private String bookshelfName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf_edit);

        setUpComponents();

        Intent i = getIntent();
        manager = (BookshelvesManager) i.getSerializableExtra("manager");
        bookshelfName = i.getStringExtra("bookshelfName");
        if(bookshelfName.equalsIgnoreCase("new")) {
            bookshelf = new Bookshelf();
            collection.setChecked(true);
        } else {
            bookshelf = manager.getBookshelf(bookshelfName);
            nameEditing.setText(bookshelf.getName());
            if(bookshelf.getType() == BookshelfType.COLLECTION)
                collection.setChecked(true);
            collection.setChecked(true);
            title.setText("Edit Bookshelf");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nameEditing.getText().toString().matches("")) {
                    if(radioGroup.getCheckedRadioButtonId() != -1) {
                        if(!manager.bookshelfExists(nameEditing.getText().toString()) ||
                                (title.getText().toString().equalsIgnoreCase("Edit Bookshelf") &&
                                        bookshelf.getName().equals(nameEditing.getText().toString()))) {
                            saveBookshelf();
                            getIntent().putExtra("manager", manager);
                            setResult(Activity.RESULT_OK, getIntent());
                            finish();
                        } else {
                            nameEditing.setError("That name is taken by other bookshelf.");
                        }
                    }
                } else {
                    nameEditing.setError("The name field cannot be empty.");
                }
            }
        });
    }


    private void saveBookshelf() {
        if(wishlist.isChecked())
            bookshelf.setType(BookshelfType.WISHLIST);
        else
            bookshelf.setType(BookshelfType.COLLECTION);
        bookshelf.setName(nameEditing.getText().toString());
        bookshelf.setDateCreated(System.currentTimeMillis() / 1000);
        manager.addBookshelf(bookshelfName, bookshelf);
    }

    /**
     * Defines all components used by the activity.
     * */
    private void setUpComponents() {
        title = findViewById(R.id.bookshelfEditorTitle);
        nameEditing = findViewById(R.id.bookshelfEditorName);
        wishlist = findViewById(R.id.wishlistRadioBtn);
        collection = findViewById(R.id.collectionRadioBtn);
        saveBtn = findViewById(R.id.bookshelfEditorSave);
        radioGroup = findViewById(R.id.bookshelfEditorRGroup);
    }
}
