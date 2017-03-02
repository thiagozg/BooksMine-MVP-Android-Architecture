package br.com.booksmine.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.booksmine.R;
import br.com.booksmine.databinding.FragmentMyCollectionBinding;
import br.com.booksmine.model.realm.po.RealmBook;
import br.com.booksmine.mvp.CollectionMVP;
import br.com.booksmine.presenter.MyCollectionPresenter;
import br.com.booksmine.view.adapter.CollectionRecyclerViewAdapter;
import br.com.booksmine.view.listener.ClickListener;
import br.com.booksmine.view.listener.LongClickListener;
import io.realm.RealmResults;

/**
 * Developed by.:   @thiagozg on 12/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class MyCollectionFragment extends Fragment
        implements CollectionMVP.GridView {

    private static CollectionMVP.GridView viewInstance;
    private static CollectionMVP.GridPresenter presenter;
    private RealmResults<RealmBook> realmBooks;

    public static MyCollectionFragment getViewInstance() {
        if (viewInstance == null) {
            viewInstance = new MyCollectionFragment();

            if (presenter == null) {
                presenter = new MyCollectionPresenter();
            }
        }

        return (MyCollectionFragment) viewInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.realmBooks = presenter.getMyCollection();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMyCollectionBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_my_collection,
                container,
                false
        );

        CollectionRecyclerViewAdapter adapter = new CollectionRecyclerViewAdapter(
                getActivity(), this.realmBooks, true, true,
                realmBook -> {
                    if (getActivity() instanceof LongClickListener) {
                        LongClickListener listener = (LongClickListener) getActivity();
                        listener.onBookLongClick(realmBook);
                    }
                },
                (book, bundle) -> {
                    if (getActivity() instanceof ClickListener) {
                        ClickListener listener = (ClickListener) getActivity();
                        listener.onBookClick(book, bundle);
                    }
                }
        );

        binding.rvCollection.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void removeBookFromMyCollection(RealmBook realmBook) {
        presenter.removeBook(realmBook);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.closeRealm();
    }

}
