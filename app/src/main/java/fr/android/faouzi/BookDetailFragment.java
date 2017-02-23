package fr.android.faouzi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookDetailFragment extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_fragment, container, false);
        Book book = getArguments().getParcelable("book");
        if(book != null)
            bindView(view, book);
        return view;
    }
    private void bindView(View view, Book b)
    {
        TextView title = (TextView) view.findViewById(R.id.book_name);
        TextView price = (TextView) view.findViewById(R.id.book_price);
        ImageView imageView = (ImageView) view.findViewById(R.id.book_image);
        TextView description = (TextView) view.findViewById(R.id.book_description);

        title.setText(b.title);
        price.setText(b.price + " €");
        Glide.with(imageView.getContext()).load(b.cover).into(imageView);
        String descriptionString = "";
        for (String desc: b.synopsis) {
            descriptionString += desc + "\n";
        }
        description.setText(descriptionString);

    }
}
