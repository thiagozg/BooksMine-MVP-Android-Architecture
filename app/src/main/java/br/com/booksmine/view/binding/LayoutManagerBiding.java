package br.com.booksmine.view.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Developed by.:   @thiagozg on 29/01/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class LayoutManagerBiding {

    @BindingAdapter({"bind:layout_manager"})
    public static void setLayoutManager(RecyclerView recyclerView, String layout){
        if ("linear".equalsIgnoreCase(layout)){
            setLayoutManager(recyclerView, layout, 0);
        }
    }

    @BindingAdapter({"bind:layout_manager", "bind:columns"})
    public static void setLayoutManager(RecyclerView recyclerView, String layout, int columns){
        if ("linear".equalsIgnoreCase(layout)){
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        } else if ("grid".equalsIgnoreCase(layout)){
            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), columns));
        }
    }

}
