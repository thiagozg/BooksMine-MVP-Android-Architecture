package br.com.booksmine.mvp;

import android.view.View;

import br.com.booksmine.model.pojo.Book;
import br.com.booksmine.model.realm.po.RealmBook;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Developed by.:   @thiagozg on 08/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public interface CollectionMVP {

    interface Model {
        int PERSIST_PROBLEM = -1;
        int PERSIST_OK = 1;
        int BOOK_EXISTS_NOT_EXISTS = 0;

        int saveBook(Book book);
        RealmBook getBook(RealmBook book);
        Observable<RealmResults<RealmBook>> getCollection();
        int removeBook(RealmBook realmBook);
        void openRealm();
        void closeRealm();
    }

    interface AddView {
        void addBookToCollection(View view);
    }

    interface GridView {
        void updateGridView();
        void removeBookFromMyCollection(RealmBook realmBook);
        void showError();
    }

    interface AddPresenter {
        int saveBook(Book book);
        void closeRealm();
    }

    interface GridPresenter {
        void getMyCollection();
        int removeBook(RealmBook realmBook);
        void closeRealm();
    }

}
