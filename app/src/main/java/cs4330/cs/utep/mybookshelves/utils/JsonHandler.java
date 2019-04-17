package cs4330.cs.utep.mybookshelves.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs4330.cs.utep.mybookshelves.manager.Book;

public class JsonHandler {

    /**
     * @author Jesus Gomez
     *
     * Class that reads the json file that is obtained
     * from the GoogleBooksAPI and assigns it to the book.
     *
     * Class: CS4330
     * Instructor: Dr. Cheon
     * Assignment: Final project
     * Date of last modification: 04/17/2019
     **/

    /** Variables */
    private String jsonData;
    private Book book;
    private int pos;

    /** Constructor */
    public JsonHandler(String jsonData, Book book) {
        this.jsonData = jsonData;
        this.book = book;

        try {
            JSONObject jsonObj = new JSONObject(jsonData);
            pos = jsonObj.getInt("totalItems") - 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls all others methods in this class in order to read
     * the json file and assign the information into the book.
     *
     * @return The book with all the info gathered from the json data.
     * */
    public Book getBookData() {
        getTitle();
        getNumPages();
        getAuthors();
        getPublishDate();
        getRating();
        getLanguage();
        getImgUrl();
        return this.book;
    }

    /**
     * Gets the number of pages by knowing where to look in the json file.
     * */
    private void getNumPages() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            book.setNumPages(jsonObj.getInt("pageCount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the rating of the book by knowing where to look in the json file.
     * */
    private void getRating() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            book.setRating(jsonObj.getDouble("averageRating"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the language of the book by knowing where to look in the json file.
     * */
    private void getLanguage() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            book.setLanguage(jsonObj.getString("language"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all the authors of the book by knowing where to look in the json file.
     * */
    private void getAuthors() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            if(jsonObj.has("authors")) {
                JSONArray authors = jsonObj.getJSONArray("authors");
                String author = "";
                for (int i = 0; i < authors.length(); i++) {
                    author += authors.getString(i) + " ";
                }
                book.setAuthor(author);
            } else {
                if(pos > 0) {
                    pos -= 1;
                    getAuthors();
                } else {
                    book.setAuthor("Not found");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the url of the image of the book by knowing where to look in the json file.
     * */
    private void getImgUrl() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            jsonObj = jsonObj.getJSONObject("imageLinks");
            book.setImgURL(jsonObj.getString("smallThumbnail"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the url of the image of the book by knowing where to look in the json file.
     * */
    private void getTitle() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            book.setTitle(jsonObj.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the published year of the book by knowing where to look in the json file.
     * */
    private void getPublishDate() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            book.setPublishedDate(jsonObj.getInt("publishedDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Breaks down the json file by looking only for the given field.
     *
     * @param field The piece of json file that we want to get.
     * @return      The piece of json file as a JSONObject
     * */
    private JSONObject get(String field) {
        try {
            JSONObject jsonObj = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObj.getJSONArray("items");
            JSONObject itemsObj = jsonArray.getJSONObject(pos);
            return itemsObj.getJSONObject(field);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}