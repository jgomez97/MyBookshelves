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
import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.manager.BookshelfType;

public class ListAdapterBookshelves extends BaseAdapter {

    private static LayoutInflater inflater = null;

    /** List of all bookshelves. */
    private List<Bookshelf> bookshelves;

    /** Constructor */
    public ListAdapterBookshelves (Context context, List<Bookshelf> bookshelves) {
        this.bookshelves = bookshelves;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = inflater.inflate(R.layout.bookshelves_element, null);
        TextView bookshelfName = v.findViewById(R.id.bookshelfName);
        TextView numBooks = v.findViewById(R.id.bookshelfNumBooks);
        TextView typeLabel = v.findViewById(R.id.bookshelfTypeLabel);
        ImageView icon = v.findViewById(R.id.bookShelfImage);
        bookshelfName.setText("Name: " + bookshelves.get(i).getName());
        numBooks.setText(String.valueOf(bookshelves.get(i).getNumBooks()) + ((bookshelves.get(i).getNumBooks() == 1) ? " book" : " books"));
        typeLabel.setText("Type: " + ((bookshelves.get(i).getType() == BookshelfType.COLLECTION) ? " Collection" : " Wishlist"));
        icon.setTag(i);

        return v;
    }

    @Override
    public int getCount() {
        return bookshelves.size();
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
