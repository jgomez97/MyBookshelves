package cs4330.cs.utep.mybookshelves.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cs4330.cs.utep.mybookshelves.R;
import cs4330.cs.utep.mybookshelves.manager.Book;

public class ListAdapterBookshelf extends BaseAdapter {
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
        bookTitle.setText("Name: " + books.get(i).getTitle());
        icon.setTag(i);
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
}
