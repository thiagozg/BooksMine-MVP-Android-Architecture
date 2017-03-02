package br.com.booksmine.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Developed by.:   @thiagozg on 29/01/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public class SearchResult {

    @SerializedName("items")
    private List<Book> listBooks;

    private String className;

    public List<Book> getListBooks() {
        return listBooks;
    }

    public void setListBooks(List<Book> listBooks) {
        this.listBooks = listBooks;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
