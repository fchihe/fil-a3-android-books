package fr.android.faouzi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chihe on 21/02/2017.
 */

public interface HenriPotierService {
        @GET("/books")
        Call<List<Book>> getBooks();
}

