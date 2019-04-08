package cs4330.cs.utep.mybookshelves;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cs4330.cs.utep.mybookshelves.manager.BookshelvesManager;
import cs4330.cs.utep.mybookshelves.utils.ListAdapterBookshelves;

public class MainActivity extends AppCompatActivity {

    /** Managers */
    private static BookshelvesManager manager;

    private TextView mainTitle;

    private ListView bookshelves;

    private MenuItem editor;

    private static boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!getIntent().hasExtra("manager")) {
            manager = new BookshelvesManager(this);
        } else {
            manager = (BookshelvesManager) getIntent().getSerializableExtra("manager");
        }



        setUpComponents();
        updateList();
        //allows to use context menu
        registerForContextMenu(bookshelves);

        bookshelves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView bookShelfName = view.findViewById(R.id.bookshelfName);
                String name = bookShelfName.getText().toString().replace("Name: ", "");
                Intent intent = new Intent(view.getContext(), BookshelfActivity.class);
                intent.putExtra("bookshelfName", name);
                intent.putExtra("manager", manager);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        editor = menu.findItem(R.id.mainMenuItem2);
        if(manager.isEmpty()) {
            editor.setVisible(false);
            editor.setEnabled(false);
        } else {
            editor.setVisible(true);
            editor.setEnabled(true);
        }

        return true;
    }
    /**
     * Allows to create a context menu.
     * */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        getMenuInflater().inflate(R.menu.contextmenu_bookshelf, menu);
    }
    /***
     *Options on context menu. The context menu allows user to select a specific item and delete it.
     * list is updated after item is deleted
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info  = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item_option:
                manager.deleteBookshelf(manager.getBookShelvesInArray().get(info.position).getName());
                updateList();

                Toast.makeText(this, "Item deleted.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mainMenuItem1:
                Intent i = new Intent(this, BookshelfEditor.class);
                i.putExtra("manager", manager);
                i.putExtra("bookshelfName", "new");
                startActivityForResult(i, 1);
                break;
            case R.id.mainMenuItem2:
                editMode = editMode ? false : true;
                editor.setTitle(editMode ? "Exit Editor" : "Bookshelves Editor");
                updateList();
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
                manager = (BookshelvesManager) data.getSerializableExtra("manager");
                updateList();
            }
        }
    }

    private void updateList() {
        bookshelves.setAdapter(new ListAdapterBookshelves(this, editMode, manager));
        if(manager.isEmpty())
            mainTitle.setText("Your Bookshelves: None");
        else {
            mainTitle.setText("Your Bookshelves:");
        }
    }

    /**
     * Defines all components used by the activity.
     * */
    private void setUpComponents() {
        mainTitle = findViewById(R.id.mainTitle);
        bookshelves = findViewById(R.id.bookshelvesList);
    }
}
