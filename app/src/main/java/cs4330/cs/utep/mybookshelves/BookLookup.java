package cs4330.cs.utep.mybookshelves;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cs4330.cs.utep.mybookshelves.manager.Book;
import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.utils.JsonHandler;

public class BookLookup extends AppCompatActivity {

    private Bookshelf bookshelf;
    private Book book;

    /** TextView */
    private TextView lookupISBN;

    /** Button */
    private Button searchBtn;

    /** Strings */
    String isbn = "", link = "", text = "";
    final private String key = "AIzaSyDeCpmRGZA0nTEgle1DUUq-RHLt9P8iwfU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lookup);

        setUpComponents();

        Intent i = getIntent();
        bookshelf = (Bookshelf) i.getSerializableExtra("bookshelf");
        if(i.hasExtra("isbn")) {
           lookupISBN.setText(i.getStringExtra("isbn"));
        }
        book = new Book();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lookupISBN.getText().toString().matches("")) {
                    if (lookupISBN.getText().toString().length() == 13) {
                        isbn = lookupISBN.getText().toString();
                        link = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + key;
                        new GoogleApiRequest().execute();
                    } else {
                        lookupISBN.setError("Please make sure you are introducing the full ISBN (13 characters) of the book.");
                    }
                } else {
                    lookupISBN.setError("The isbn field cannot be empty.");
                }
            }
        });
    }

    private class GoogleApiRequest extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if(isCancelled()){
                return null;
            }
            try {
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    text = text + line;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(!bookIsOnAPI()) {
                lookupISBN.setError("ISBN not found!");
                this.cancel(true);
            } else {
                book.setIsbn(Long.parseLong(lookupISBN.getText().toString()));
                book.setPrice(1.99);
                book.setBookshelfName(bookshelf.getName());
                JsonHandler reader = new JsonHandler(text, book);
                book = reader.getBookData();

                bookshelf.addBook("new", book);
                getIntent().putExtra("bookshelf", bookshelf);
                setResult(Activity.RESULT_OK, getIntent());
                finish();
            }
        }
    }

    private boolean bookIsOnAPI() {
        try {
            JSONObject jObject = new JSONObject(text);
            if(jObject.getInt("totalItems") >= 1)
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Defines all components used by the activity.
     * */
    private void setUpComponents () {
        lookupISBN = findViewById(R.id.isbnLookup);
        searchBtn = findViewById(R.id.isbnButtonLookup);
    }
}