package br.com.booksmine.presenter;

import br.com.booksmine.model.dao.CollectionDAO;
import br.com.booksmine.model.realm.po.RealmBook;
import br.com.booksmine.mvp.CollectionMVP;
import io.realm.RealmResults;

/**
 * Developed by.:   @thiagozg on 23/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class MyCollectionPresenter implements CollectionMVP.GridPresenter {

    private CollectionMVP.Model model;

    public MyCollectionPresenter() {
        this.model = new CollectionDAO();
    }

    @Override
    public RealmResults<RealmBook> getMyCollection() {
        return this.model.getCollection();
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
