package cs4330.cs.utep.mybookshelves.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import 	android.app.AlertDialog;

import java.util.List;

import cs4330.cs.utep.mybookshelves.BookshelfActivity;
import cs4330.cs.utep.mybookshelves.BookshelfEditor;
import cs4330.cs.utep.mybookshelves.MainActivity;
import cs4330.cs.utep.mybookshelves.R;
import cs4330.cs.utep.mybookshelves.manager.Bookshelf;
import cs4330.cs.utep.mybookshelves.manager.BookshelfType;
import cs4330.cs.utep.mybookshelves.manager.BookshelvesManager;

public class ListAdapterBookshelves extends BaseAdapter {

    private BookshelvesManager manager;

    private static LayoutInflater inflater = null;
    private CalendarUtil dateUtil = new CalendarUtil();

    /** List of all bookshelves. */
    private List<Bookshelf> bookshelves;

    private Boolean editMode;

    private Context context;

    /** Constructor */
    public ListAdapterBookshelves (Context context, boolean editMode, BookshelvesManager manager) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.editMode = editMode;
        this.context = context;
        this.manager = manager;
        this.bookshelves = manager.getBookShelvesInArray();
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final View v = inflater.inflate(R.layout.bookshelves_element, null);

        TextView bookshelfName = v.findViewById(R.id.bookshelfName);
        TextView numBooks = v.findViewById(R.id.bookshelfNumBooks);
        TextView typeLabel = v.findViewById(R.id.bookshelfTypeLabel);
        TextView dateLabel = v.findViewById(R.id.bookshelfDate);
        ImageView icon = v.findViewById(R.id.bookShelfImage);
        ImageView editIcon = v.findViewById(R.id.editIcon);
        if(editMode)
            editIcon.setVisibility(View.VISIBLE);

        bookshelfName.setText(Html.fromHtml("<b>Name: </b>" + bookshelves.get(i).getName()));
        numBooks.setText(Html.fromHtml("<b>Contains: </b>" + String.valueOf(bookshelves.get(i).getNumBooks()) + ((bookshelves.get(i).getNumBooks() == 1) ? " book" : " books")));
        typeLabel.setText(Html.fromHtml("<b>Type: </b>" + ((bookshelves.get(i).getType() == BookshelfType.COLLECTION) ? " Collection" : " Wish-list")));
        dateLabel.setText(Html.fromHtml("<b>Date Created: </b>" + "<i>" + dateUtil.convertMillisecondsToDate(bookshelves.get(i).getDateCreated()) + "</i>"));
        icon.setTag(i);
        editIcon.setTag(i);

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (Integer)v.findViewById(R.id.editIcon).getTag();
                String bookshelfName = bookshelves.get(i).getName();
                Intent intent = new Intent(context, BookshelfEditor.class);
                intent.putExtra("manager", manager);
                intent.putExtra("bookshelfName", bookshelfName);
                context.startActivity(intent);
                return;
            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (Integer)v.findViewById(R.id.bookShelfImage).getTag();
                String bookshelfName = bookshelves.get(i).getName();

                Intent intent = new Intent(view.getContext(), BookshelfActivity.class);
                intent.putExtra("bookshelfName", bookshelfName);
                intent.putExtra("manager", manager);
                context.startActivity(intent);
            }
        });

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
