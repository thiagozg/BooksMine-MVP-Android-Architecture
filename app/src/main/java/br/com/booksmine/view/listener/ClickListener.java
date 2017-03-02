package br.com.booksmine.view.listener;

import br.com.booksmine.model.pojo.Book;

/**
 * Developed by.:   @thiagozg on 29/01/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */

public interface ClickListener {

    void onBookClick(Book book, boolean bundle);

}
