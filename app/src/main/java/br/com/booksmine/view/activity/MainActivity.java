package br.com.booksmine.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.parceler.Parcels;

import br.com.booksmine.R;
import br.com.booksmine.model.pojo.Book;
import br.com.booksmine.model.realm.po.RealmBook;
import br.com.booksmine.view.fragment.GoogleBooksListFragment;
import br.com.booksmine.view.fragment.MyCollectionFragment;
import br.com.booksmine.view.listener.ClickListener;
import br.com.booksmine.view.listener.LongClickListener;

/**
 * Developed by.:   @thiagozg on 29/01/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,
                    ClickListener, LongClickListener {

    public static final String SEARH_ACTIVE = "searhActive";

    private SearchView searchView;
    private MyCollectionFragment myCollectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.myCollectionFragment = MyCollectionFragment.getViewInstance();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        this.showMyCollection();
    }

    private void showMyCollection() {
        if (!getIntent().getBooleanExtra(SEARH_ACTIVE, false)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list, myCollectionFragment, "myCollection")
                    .commit();
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getIntent().putExtra(SEARH_ACTIVE, true);
        GoogleBooksListFragment bookListFragment = GoogleBooksListFragment.getViewInstance();

        if (!bookListFragment.isVisible() || !bookListFragment.isResumed()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list, bookListFragment, "searchList")
                    .commit();

            bookListFragment.searhByQuery(query);
        }

        this.searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        getIntent().putExtra(SEARH_ACTIVE, false);
                        showMyCollection();
                        return true;
                    }
                });
        this.searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        this.searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public void onBookClick(Book book, boolean bundle) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        Parcelable parcelable = Parcels.wrap(book);
        intent.putExtra(BookDetailActivity.BOOK_OBJECT, parcelable);
        intent.putExtra(BookDetailActivity.SEARCH_DETAIL, bundle);
        startActivity(intent);
    }

    @Override
    public void onBookLongClick(RealmBook realmBook) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(String.format(
                getString(R.string.dialog_message), realmBook.getTitle()));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                (dialog, which) -> myCollectionFragment.removeBookFromMyCollection(realmBook));

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onFloatButtonClick(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    /**
     * Quando acontece a busca, e a tela é rodada, é preciso lidar com o botão voltar para
     * que o aplicativo não seja fechado, pois quando a activity ser recriada
     * o onMenuItemActionCollapse não será mais chamado ao pressionar o botão boltar.
     */
    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra(SEARH_ACTIVE, false)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_list, myCollectionFragment, "myCollection")
                    .commit();

            getIntent().putExtra(SEARH_ACTIVE, false);
        } else {
            super.onBackPressed();
        }
    }
}
