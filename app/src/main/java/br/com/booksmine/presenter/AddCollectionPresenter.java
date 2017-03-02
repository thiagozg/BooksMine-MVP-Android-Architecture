package br.com.booksmine.presenter;

import br.com.booksmine.model.dao.CollectionDAO;
import br.com.booksmine.model.pojo.Book;
import br.com.booksmine.mvp.CollectionMVP;

/**
 * Developed by.:   @thiagozg on 19/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class AddCollectionPresenter implements CollectionMVP.AddPresenter {

    private CollectionMVP.Model model;

    public AddCollectionPresenter() {
        this.model = new CollectionDAO();
    }

    @Override
    public int saveBook(Book book) {
        return this.model.saveBook(book);
    }

    @Override
    public void closeRealm() {
        this.model.closeRealm();
    }

}
