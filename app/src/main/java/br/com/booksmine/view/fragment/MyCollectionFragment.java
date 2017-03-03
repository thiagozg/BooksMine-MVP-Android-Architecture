package br.com.booksmine.view.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.tool.Binding;
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

import br.com.booksmine.R;
import br.com.booksmine.databinding.FragmentMyCollectionBinding;
import br.com.booksmine.model.pojo.SearchResult;
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
    private FragmentMyCollectionBinding binding;

    public static MyCollectionFragment getViewInstance() {
        if (viewInstance == null) {
            viewInstance = new MyCollectionFragment();

            if (presenter == null) {
                presenter = new MyCollectionPresenter(viewInstance);
            }
        }

        return (MyCollectionFragment) viewInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_my_collection,
                container,
                false
        );

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMyCollection();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(RealmResults<RealmBook> realmBooks) {
        this.realmBooks = realmBooks;
        this.updateGridView();
        EventBus.getDefault().removeStickyEvent(this);
    }

    @Override
    public void updateGridView() {
        if (this.realmBooks != null) {

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

            this.binding.rvCollection.setAdapter(adapter);
        }
    }

    @Override
    public void removeBookFromMyCollection(RealmBook realmBook) {
        presenter.removeBook(realmBook);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.msg_error_search_books, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.closeRealm();
        EventBus.getDefault().unregister(this);
    }

}
