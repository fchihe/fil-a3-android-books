package fr.android.faouzi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryActivity extends AppCompatActivity implements View.OnClickListener, Callback<List<Book>> {

    private ArrayList<Book> books;
    private final String bookListKey = "books";
    private final String bookKey = "book";
    private Book selectedBook;
    private final String NO_INTERNET = "Pas de connexion à internet, veuillez vous connecter à internet et relancer l'application";
    private final String NO_BOOKS = "Impossible de charger les livres, veuillez réessayer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getBoolean(R.bool.twoPaneMode))
            setContentView(R.layout.activity_library_land);
        else
            setContentView(R.layout.activity_library);
        if(!isOnline()){
            updateState(NO_INTERNET);
            return;
        }
        if (savedInstanceState == null) {
            getBooksFromService();
        } else {
            books = savedInstanceState.getParcelableArrayList(bookListKey);
            selectedBook = savedInstanceState.getParcelable(bookKey);
            initBookList();
            initBookDetail();
        }


    }

    private void updateState(String message) {
        TextView state = (TextView) findViewById(R.id.state_text);
        ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        state.setText(message);
        loadingSpinner.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        selectedBook = books.get((int) v.getTag());
        initBookDetail();
    }

    private void initBookDetail() {
        if(selectedBook == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putParcelable(bookKey, selectedBook);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        bookDetailFragment.setArguments(bundle);
        if(getResources().getBoolean(R.bool.twoPaneMode))
        {
            ft.replace(R.id.book_detail_fragment, bookDetailFragment, "detail");
        }
        else
        {
            ft.replace(R.id.fragment_container, bookDetailFragment, "detail");
            ft.addToBackStack("detail");
        }

        ft.commit();
    }

    public void getBooksFromService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HenriPotierService service = retrofit.create(HenriPotierService.class);
        Call<List<Book>> booksPromise = service.getBooks();
        booksPromise.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
        this.books = (ArrayList<Book>) response.body();
        //Pass books to Book List Fragment
        initBookList();

    }

    private void initBookList() {
        if(books == null)
            return;

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(bookListKey, books);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        BookListFragment bookListFragment = new BookListFragment();
        bookListFragment.setArguments(bundle);

        if(getResources().getBoolean(R.bool.twoPaneMode))
        {
            //Remove all elements
            ViewGroup group = (ViewGroup) this.findViewById(R.id.book_list_fragment);
            group.removeAllViews();
            ft.replace(R.id.book_list_fragment, bookListFragment, "list");
        }
        else
        {
            //Remove all elements
            ViewGroup group = (ViewGroup) this.findViewById(R.id.fragment_container);
            group.removeAllViews();
            //Put list in the container
            ft.replace(R.id.fragment_container, bookListFragment, "list");
        }
        ft.commit();
    }

    @Override
    public void onFailure(Call<List<Book>> call, Throwable t) {
        updateState(NO_BOOKS);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(bookListKey, books);
        outState.putParcelable(bookKey, selectedBook);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
