package br.com.booksmine.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.com.booksmine.R;
import br.com.booksmine.databinding.FragmentListBookBinding;
import br.com.booksmine.model.pojo.Book;
import br.com.booksmine.model.pojo.SearchResult;
import br.com.booksmine.mvp.GoogleBookMVP;
import br.com.booksmine.presenter.GoogleBookPresenter;
import br.com.booksmine.view.adapter.GoogleBookAdapter;
import br.com.booksmine.view.listener.ClickListener;

/**
 * Developed by.:   @thiagozg on 12/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class GoogleBookListFragment extends Fragment
        implements GoogleBookMVP.View {

    private static GoogleBookMVP.View viewInstance;
    private static GoogleBookMVP.Presenter presenter;
    private List<Book> listBooks;
    private GoogleBookAdapter adapter;
    private FragmentListBookBinding binding;

    public static GoogleBookListFragment getViewInstance() {
        if (viewInstance == null) {
            viewInstance = new GoogleBookListFragment();

            if (presenter == null) {
                presenter = new GoogleBookPresenter(viewInstance);
            }
        }

        return (GoogleBookListFragment) viewInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        listBooks = new ArrayList<>();

        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_list_book,
                container,
                false);

        adapter = new GoogleBookAdapter(listBooks,
                (book, bundle) -> {
                    if (getActivity() instanceof ClickListener) {
                        ClickListener listener = (ClickListener) getActivity();
                        listener.onBookClick(book, bundle);
                    }
        });

        return binding.getRoot();
    }

    @Override
    public void searhByQuery(String query){
        presenter.searchListOfBooks(query);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SearchResult event) {
        this.updateListView(event);
        EventBus.getDefault().removeStickyEvent(this);
    }

    @Override
    public void updateListView(SearchResult searchResult){
        if (searchResult != null) {
            this.listBooks.clear();

            if (searchResult.getListBooks() != null && searchResult.getListBooks().size() > 0) {
                this.listBooks.addAll(searchResult.getListBooks());
                binding.rvBooks.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                this.showNoResults();
            }

            return;
        }

        this.showError();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.msg_error_search_books, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoResults() {
        Toast.makeText(getActivity(), R.string.msg_warning_no_results, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
