package cs4330.cs.utep.mybookshelves.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs4330.cs.utep.mybookshelves.R;
import cs4330.cs.utep.mybookshelves.manager.Book;

/**
 * @author Jesus Gomez
 *
 * Class that creates a custom dialog that
 * contains all the information of certain book.
 *
 * Class: CS4330
 * Instructor: Dr. Cheon
 * Assignment: Final project
 * Date of last modification: 04/17/2019
 **/

public class CustomDialog {

    /** Components */
    private TextView title, authors, pages, isbn, date, language, year, rating;
    private Button closeBtn;

    /** Utils */
    CalendarUtil dateUtil = new CalendarUtil();

    /** Constructor */
    public CustomDialog(Context context, Book book) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom);

        setUpComponents(dialog);

        title.setText(book.getTitle());
        authors.setText(Html.fromHtml("<b>Author(s): </b>" + book.getAuthor()));
        pages.setText(Html.fromHtml("<b>Pages: </b>" +book.getNumPages()));
        isbn.setText(Html.fromHtml("<b>ISBN(13): </b>" + book.getIsbn()));
        date.setText(Html.fromHtml("<b>Added on: </b>" + dateUtil.convertMillisecondsToDate(book.getDateAdded())));
        language.setText(Html.fromHtml("<b>Language: </b>" + book.getLanguage()));
        year.setText(Html.fromHtml("<b>Year: </b>" + book.getPublishedDate()));
        rating.setText(Html.fromHtml("<b>Rating: </b>" + book.getRating()));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * Defines all the components used by the dialog.
     * */
    public void setUpComponents(Dialog dialog) {
        title = dialog.findViewById(R.id.titleDialog);
        authors = dialog.findViewById(R.id.authorDialog);
        pages = dialog.findViewById(R.id.numPagesDialog);
        isbn = dialog.findViewById(R.id.isbnDialog);
        date = dialog.findViewById(R.id.dateDialog);
        language = dialog.findViewById(R.id.languageDialog);
        year = dialog.findViewById(R.id.yearDialog);
        rating = dialog.findViewById(R.id.ratingDialog);
        closeBtn = dialog.findViewById(R.id.closeBtnDialog);
    }
}
