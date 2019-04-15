package cs4330.cs.utep.mybookshelves;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.manager.BookshelvesManager;
import cs4330.cs.utep.mybookshelves.utils.ListAdapterBookshelf;
import cs4330.cs.utep.mybookshelves.utils.ListAdapterBookshelves;

public class BookshelfActivity extends AppCompatActivity {

    /** Managers */
    private static BookshelvesManager manager;
    private static Bookshelf bookshelf;

    private TextView mainTitle;

    private GridView books;

    private String oldBookshelfName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        oldBookshelfName = getIntent().getStringExtra("bookshelfName");

        manager = (BookshelvesManager) getIntent().getSerializableExtra("manager");
        bookshelf = manager.getBookshelf(oldBookshelfName);

        setUpComponents();
        updateGrid();

        //allows to use context menu
        registerForContextMenu(books);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookshelf_menu, menu);
        return true;
    }
    /**
     * Allows to create a context menu.
     * */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        getMenuInflater().inflate(R.menu.contextmenu_books, menu);
    }
    /**
     * Allows to create a context menu.
     * */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int viewToEditID;
        String viewToEditName;
        AdapterView.AdapterContextMenuInfo info  = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item_option_books:
                bookshelf.deleteBook(bookshelf.getBooksInArray().get(info.position).getTitle());
                updateGrid();
                Toast.makeText(this, "Item deleted.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.edit_item_option_books:
                String name = bookshelf.getBooksInArray().get(info.position).getTitle();

                Intent  i = new Intent(this,BookEditor.class);
                i.putExtra("bookName", name);
                i.putExtra("bookshelf", bookshelf);
                startActivityForResult(i, 1);
                return true;

            default:
                return super.onContextItemSelected(item);

        }
        // return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.bookshelfMenuItem1:
                Intent i = new Intent(this, BookLookup.class);
                i.putExtra("bookshelf", bookshelf);
                startActivityForResult(i, 1);
                break;
            case R.id.bookshelfMenuItem2:
                Intent intent = new Intent(this, ScanBarcodeActivity.class);
                startActivityForResult(intent,0);
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
                manager.addBookshelf(oldBookshelfName, bookshelf);
                updateGrid();
            }
        } else if(requestCode == 0) {
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    Intent i = new Intent(this, BookLookup.class);
                    i.putExtra("bookshelf", bookshelf);
                    i.putExtra("isbn", barcode.displayValue);
                    startActivityForResult(i, 1);
                }
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
