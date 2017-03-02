package br.com.booksmine.model.http;

import br.com.booksmine.model.pojo.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Developed by.:   @thiagozg on 29/01/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public interface GoogleBooksService {

    public static final String URL_BASE = "https://www.googleapis.com/books/v1/volumes/";

    @GET("./")
    Observable<SearchResult> searchBook(@Query("q") String q);

}
