package cs4330.cs.utep.mybookshelves.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs4330.cs.utep.mybookshelves.manager.Book;

public class JsonHandler {

    private String jsonData;
    private Book book;
    private int pos;

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

    public Book getBookData() {
        getTitle();
        getNumPages();
        getAuthors();
        return this.book;
    }

    public void getNumPages() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            book.setNumPages(jsonObj.getInt("pageCount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAuthors() {
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

    public void getTitle() {
        try {
            JSONObject jsonObj = get("volumeInfo");
            book.setTitle(jsonObj.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject get(String text) {
        try {
            JSONObject jsonObj = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObj.getJSONArray("items");
            JSONObject itemsObj = jsonArray.getJSONObject(pos);
            return itemsObj.getJSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
