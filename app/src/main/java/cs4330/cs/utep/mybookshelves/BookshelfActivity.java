package cs4330.cs.utep.mybookshelves;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.manager.BookshelvesManager;
import cs4330.cs.utep.mybookshelves.utils.ListAdapterBookshelf;
import cs4330.cs.utep.mybookshelves.utils.ListAdapterBookshelves;

public class BookshelfActivity extends AppCompatActivity {

    /** Managers */
    private static Bookshelf bookshelf;

    private TextView mainTitle;

    private GridView books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        bookshelf = (Bookshelf) getIntent().getSerializableExtra("bookshelfName");

        setUpComponents();
        updateGrid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookshelf_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.bookshelfMenuItem1:
                Intent i = new Intent(this, BookEditor.class);
                i.putExtra("bookshelf", bookshelf);
                i.putExtra("bookName", "new");
                startActivityForResult(i, 1);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                bookshelf = (Bookshelf) data.getSerializableExtra("bookshelf");
                updateGrid();
            }
        }
    }


    private void updateGrid() {
        books.setAdapter(new ListAdapterBookshelf(this, bookshelf.getBooksInArray()));
        mainTitle.setText(bookshelf.getName());
    }

    /**
     * Defines all components used by the activity.
     * */
    private void setUpComponents() {
        mainTitle = findViewById(R.id.bookshelfTitle);
        books = findViewById(R.id.books_list);
    }
}
