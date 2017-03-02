package br.com.booksmine.mvp;

import br.com.booksmine.model.pojo.SearchResult;
import rx.Observable;

/**
 * Developed by.:   @thiagozg on 08/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public interface GoogleBookMVP {

    interface Model {
        Observable<SearchResult> searchListOfBooks(String query);
    }

    interface View {
        void searhByQuery(String query);
        void showError();
        void showNoResults();
        void updateListView(SearchResult searchResult);
    }

    interface Presenter {
        void searchListOfBooks(String query);
    }

}
