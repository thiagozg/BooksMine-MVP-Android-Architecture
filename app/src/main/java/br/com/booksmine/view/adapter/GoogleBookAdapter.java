package br.com.booksmine.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import br.com.booksmine.R;
import br.com.booksmine.databinding.ItemBookBinding;
import br.com.booksmine.model.pojo.Book;
import br.com.booksmine.view.listener.ClickListener;

/**
 * Developed by.:   @thiagozg on 12/02/2017.
 * E-mail.:         thiagozg1995@gmail.com
 * GitHub.:         https://github.com/thiagozg/
 * Google Play.:    https://play.google.com/store/apps/developer?id=Thiago+Giacomini
 */
public class GoogleBookAdapter extends RecyclerView.Adapter<GoogleBookAdapter.ViewHolder> {

    private List<Book> listBooks;
    private ClickListener listener;

    public GoogleBookAdapter(List<Book> listBooks, ClickListener listener) {
        this.listBooks = listBooks;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_book,
                parent,
                false
        );

        ViewHolder holder = new ViewHolder(binding);
        holder.itemView.setOnClickListener(view -> {
                    if (listener != null) {
                        int position = holder.getAdapterPosition();
                        Book book = listBooks.get(position);
                        listener.onBookClick(book, true);
                    }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = listBooks.get(position);
        holder.binding.setBook(book);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return listBooks != null ? listBooks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemBookBinding binding;

        public ViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
