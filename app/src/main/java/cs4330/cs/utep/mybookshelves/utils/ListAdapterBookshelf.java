package cs4330.cs.utep.mybookshelves.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import cs4330.cs.utep.mybookshelves.R;
import cs4330.cs.utep.mybookshelves.manager.Book;

/**
 * @author Jesus Gomez
 *
 * BaseAdapter responsable for handling
 * the GridView that is displayed in the BookshelfActivity.
 * @see cs4330.cs.utep.mybookshelves.BookshelfActivity
 *
 * Contains private class which is responsable to
 * download and display the image of each book.
 *
 * Class: CS4330
 * Instructor: Dr. Cheon
 * Assignment: Final project
 * Date of last modification: 04/17/2019
 **/

public class ListAdapterBookshelf extends BaseAdapter {

    /** Components used by the adpater*/
    private static LayoutInflater inflater = null;

    /** List of all books. */
    private List<Book> books;

    /** Constructor */
    public ListAdapterBookshelf (Context context, List<Book> books) {
        this.books = books;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = inflater.inflate(R.layout.book_element, null);
        TextView bookTitle = v.findViewById(R.id.bookTiltleElement);
        ImageView icon = v.findViewById(R.id.bookImageElement);
        bookTitle.setText(books.get(i).getTitle());
        icon.setTag(i);
        new DownloadImageTask(icon).execute(books.get(i).getImgURL());
        return v;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /** Responsible for connecting/downloading image of the book and displaying it.*/
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) { //Not valid url = no image.
                cancel(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
