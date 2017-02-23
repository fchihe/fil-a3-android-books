package fr.android.faouzi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Book implements Parcelable{

    public final String title;
    public final float price;
    public final String cover;
    public final List<String> synopsis;
    public Book(String name, float price, String image, List<String> synopsis) {
        this.title = name;
        this.price = price;
        this.cover = image;
        this.synopsis = synopsis;
    }

    protected Book(Parcel in) {
        title = in.readString();
        price = in.readFloat();
        cover = in.readString();
        synopsis = in.createStringArrayList();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (Float.compare(book.price, price) != 0) return false;
        return title.equals(book.title);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeFloat(price);
        dest.writeString(cover);
        dest.writeStringList(synopsis);
    }
}
