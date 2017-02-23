package fr.android.faouzi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chihe on 21/02/2017.
 */
public class BookRecyclerAdapter extends RecyclerView.Adapter {
    private final LayoutInflater inflater;
    private List<Book> books;
    public BookRecyclerAdapter(LayoutInflater inflater, List<Book> books) {
        this.books=books;
        this.inflater = inflater;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_view_item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        BookItemView bookItemView = (BookItemView) holder.itemView;
        bookItemView.bindView(books.get(position));
        bookItemView.setTag(position);
        bookItemView.setOnClickListener((View.OnClickListener)inflater.getContext());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
