package br.com.booksmine.model.dao;

import android.util.Log;

import br.com.booksmine.model.pojo.Book;
import br.com.booksmine.model.realm.po.RealmBook;
import br.com.booksmine.model.realm.util.RealmUtil;
import br.com.booksmine.mvp.CollectionMVP;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Developed by.:   @thiagozg on 19/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public class CollectionDAO implements CollectionMVP.Model {

    private Realm realm;
    private boolean stateRealm;

    @Override
    public int saveBook(Book book) {
        this.openRealm();
        int result = BOOK_EXISTS_NOT_EXISTS;

        RealmBook realmBook = RealmUtil.convertParcelableBookToPOBook(book);

        if (this.getBook(realmBook) == null) {

            try {
                this.realm.executeTransaction(realm -> {
                    realm.copyToRealmOrUpdate(realmBook);
                });

                result = PERSIST_OK;
            } catch (Exception e) {
                Log.e("ERROR", "CollectionDAO - saveBook");
                e.printStackTrace();
                result = PERSIST_PROBLEM;
            }

        }

        return result;
    }

    @Override
    public RealmResults<RealmBook> getCollection() {
        this.openRealm();
        RealmResults<RealmBook> realmResults =
                this.realm
                        .where(RealmBook.class)
                        .findAll();

        return realmResults;
    }

    @Override
    public RealmBook getBook(RealmBook realmBook) {
        this.openRealm();
        RealmResults<RealmBook> books =
                this.realm
                        .where(RealmBook.class)
                        .equalTo(RealmBook.ID, realmBook.getId())
                        .findAll();

        if (books.size() > 0) {
            return books.get(0);
        }

        return null;
    }

    @Override
    public int removeBook(RealmBook realmBook) {
        this.openRealm();
        int result = PERSIST_PROBLEM;

        try {
            this.realm.executeTransaction(realm -> {
                realm.where(RealmBook.class)
                        .equalTo(RealmBook.ID, realmBook.getId())
                        .findAll()
                        .deleteAllFromRealm();
            });

            result = PERSIST_OK;
        } catch (Exception e) {
            Log.e("ERROR", "CollectionDAO - removeBook");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void openRealm() {
        this.stateRealm = true;
        if (this.realm == null) {
            this.realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void closeRealm() {
        if (this.stateRealm) {
            this.realm.close();
            this.realm = null;
        }
    }

}
