package fr.android.faouzi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

public class BookListFragment extends Fragment {

    private static final String step0 = "This is step 0";

    private TextView textView;
    private AdapterView.OnClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // listener = (AdapterView.OnClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list_fragment, container, false);
        List<Book> books = getArguments().getParcelableArrayList("books");
        LibraryActivity activity = (LibraryActivity) getActivity();

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.bookRecycler);
        listView.setOnClickListener(listener);
        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setAdapter(new BookRecyclerAdapter(inflater, books));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
