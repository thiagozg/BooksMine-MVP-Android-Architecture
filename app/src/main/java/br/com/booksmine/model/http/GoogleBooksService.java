package br.com.booksmine.model.http;

import br.com.booksmine.model.pojo.SearchResult;
import br.com.booksmine.mvp.GoogleBookMVP;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Developed by.:   @thiagozg on 29/01/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public class GoogleBooksService implements GoogleBookMVP.Model {

    public Observable<SearchResult> searchListOfBooks(String query) {

        RxJavaCallAdapterFactory rxAdapter =
                RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleBooksAPI.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        GoogleBooksAPI googleBooksService = retrofit.create(GoogleBooksAPI.class);
        Observable<SearchResult> searchResultObservable = googleBooksService.searchBook(query);

        return searchResultObservable;
    }

}
