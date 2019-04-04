package cs4330.cs.utep.mybookshelves.utils;

import android.content.Context;
import android.text.Html;
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
    private CalendarUtil dateUtil = new CalendarUtil();

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
        TextView dateLabel = v.findViewById(R.id.bookshelfDate);
        ImageView icon = v.findViewById(R.id.bookShelfImage);
        bookshelfName.setText(Html.fromHtml("<b>Name: </b>" + bookshelves.get(i).getName()));
        numBooks.setText(Html.fromHtml("<b>Contains: </b>" + String.valueOf(bookshelves.get(i).getNumBooks()) + ((bookshelves.get(i).getNumBooks() == 1) ? " book" : " books")));
        typeLabel.setText(Html.fromHtml("<b>Type: </b>" + ((bookshelves.get(i).getType() == BookshelfType.COLLECTION) ? " Collection" : " Wish-list")));
        dateLabel.setText(Html.fromHtml("<b>Date Created: </b>" + "<i>" + dateUtil.convertMillisecondsToDate(bookshelves.get(i).getDateCreated()) + "</i>"));
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
