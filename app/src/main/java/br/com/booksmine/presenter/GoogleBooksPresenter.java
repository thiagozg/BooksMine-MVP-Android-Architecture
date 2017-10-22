package br.com.booksmine.presenter;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import br.com.booksmine.model.http.GoogleBooksService;
import br.com.booksmine.model.pojo.SearchResult;
import br.com.booksmine.mvp.GoogleBookMVP;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Developed by.:   @thiagozg on 29/01/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public class GoogleBooksPresenter implements GoogleBookMVP.Presenter {

    private GoogleBookMVP.View view;

    private GoogleBookMVP.Model model;

    public GoogleBooksPresenter(GoogleBookMVP.View view) {
        this.view = view;
        this.model = new GoogleBooksService();
    }

    @Override
    public void searchListOfBooks(String query) {
        Observable<SearchResult> result = model.searchListOfBooks(query);
        result
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    searchResult -> EventBus.getDefault().postSticky(searchResult),
                    throwable -> {
                        view.showError();
                        throwable.printStackTrace();
                    },
                    () -> Log.d("LOG", "searchListOfBooks complete!")
            );
    }
}
