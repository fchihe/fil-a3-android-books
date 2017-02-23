package fr.android.faouzi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class BookItemView extends LinearLayout {

    private TextView priceTextView;
    private TextView nameTextView;
    private ImageView imageView;

    public BookItemView(Context context) {
        this(context, null);
    }

    public BookItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        nameTextView = (TextView) findViewById(R.id.book_name);
        priceTextView = (TextView) findViewById(R.id.book_price);
        imageView = (ImageView) findViewById(R.id.book_image);

    }

    public void bindView(Book book) {
        priceTextView.setText(book.price + " €");
        nameTextView.setText(book.title);
        Glide.with(imageView.getContext()).load(book.cover).into(imageView);

    }
}
