package br.com.booksmine.presenter;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import br.com.booksmine.model.dao.CollectionDAO;
import br.com.booksmine.model.realm.po.RealmBook;
import br.com.booksmine.mvp.CollectionMVP;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Developed by.:   @thiagozg on 23/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class MyCollectionPresenter implements CollectionMVP.GridPresenter {

    private CollectionMVP.Model model;

    private CollectionMVP.GridView view;

    public MyCollectionPresenter(CollectionMVP.GridView view) {
        this.model = new CollectionDAO();
        this.view = view;
    }

    @Override
    public void getMyCollection() {
        Observable<RealmResults<RealmBook>> results = this.model.getCollection();
        results
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    realmResults -> EventBus.getDefault().postSticky(realmResults),
                    throwable -> {
                        view.showError();
                        throwable.printStackTrace();
                    },
                    () -> Log.d("LOG", "getMyCollection complete!")
            );
    }

    @Override
    public int removeBook(RealmBook realmBook) {
        return this.model.removeBook(realmBook);
    }

    @Override
    public void closeRealm() {
        this.model.closeRealm();
    }

}
