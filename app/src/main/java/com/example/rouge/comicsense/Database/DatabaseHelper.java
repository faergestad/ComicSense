package com.example.rouge.comicsense.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rouge.comicsense.Model.Comic;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "comics";
    private static final String TABLE_FAVORITES = "favorites";

    // Column fields
    private static final String KEY_NUM = "num";
    private static final String KEY_MONTH = "month";
    private static final String KEY_LINK = "link";
    private static final String KEY_YEAR = "year";
    private static final String KEY_NEWS = "news";
    private static final String KEY_SAFE_TITLE = "safeTitle";
    private static final String KEY_TRANSCRIPT = "transcript";
    private static final String KEY_ALT = "alt";
    private static final String KEY_IMG_URL = "imgUrl";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DAY = "day";
    private static final String KEY_IMG = "img";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_NUM +" INTEGER PRIMARY KEY,"
                + KEY_MONTH +" TEXT,"
                + KEY_LINK +" TEXT,"
                + KEY_YEAR +" TEXT,"
                + KEY_NEWS +" TEXT,"
                + KEY_SAFE_TITLE +" TEXT,"
                + KEY_TRANSCRIPT +" TEXT,"
                + KEY_ALT +" TEXT,"
                + KEY_IMG_URL +" TEXT,"
                + KEY_TITLE +" TEXT,"
                + KEY_DAY +" TEXT,"
                + KEY_IMG +" BLOB" + ")";
        db.execSQL(CREATE_TABLE_FAVORITES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table, if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);

        onCreate(db);
    }

    public void addComic(Comic comic) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NUM, comic.getNum());
        values.put(KEY_MONTH, comic.getMonth());
        values.put(KEY_LINK, comic.getLink());
        values.put(KEY_YEAR, comic.getYear());
        values.put(KEY_NEWS, comic.getNews());
        values.put(KEY_SAFE_TITLE, comic.getSafeTitle());
        values.put(KEY_TRANSCRIPT, comic.getTranscript());
        values.put(KEY_ALT, comic.getAlt());
        values.put(KEY_IMG_URL, comic.getImg());
        values.put(KEY_TITLE, comic.getTitle());
        values.put(KEY_DAY, comic.getDay());
        values.put(KEY_IMG, comic.getImgBytes());

        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public List<Comic> getAllFavorites() {
        List<Comic> favoritesList = new ArrayList<>();

        String selectAll = "SELECT * FROM " + TABLE_FAVORITES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()) {
            do {
                Comic comic = new Comic();
                comic.setNum(Integer.parseInt(cursor.getString(0)));
                comic.setMonth(cursor.getString(1));
                comic.setLink(cursor.getString(2));
                comic.setYear(cursor.getString(3));
                comic.setNews(cursor.getString(4));
                comic.setSafeTitle(cursor.getString(5));
                comic.setAlt(cursor.getString(6));
                comic.setImg(cursor.getString(7));
                comic.setTitle(cursor.getString(8));
                comic.setDay(cursor.getString(9));
                comic.setImgBytes(cursor.getBlob(10));

                favoritesList.add(comic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoritesList;
    }

    public void deleteFavoritedComic(int num) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_NUM + " = ?", new String[] {String.valueOf(num)});
        db.close();
    }

}
