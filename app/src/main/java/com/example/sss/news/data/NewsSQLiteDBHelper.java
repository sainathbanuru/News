package com.example.sss.news.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.params.StreamConfigurationMap;

/**
 * Created by sss on 24/9/17.
 */

public class NewsSQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "news.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + NewsContract.newsData.TABLE_NAME + " (" +
            NewsContract.newsData.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NewsContract.newsData.COLUMN_TITLE + " TEXT NOT NULL," +
            NewsContract.newsData.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
            NewsContract.newsData.COLUMN_PUBLISHER + " TEXT NOT NULL," +
            NewsContract.newsData.COLUMN_URL2IMAGE + " TEXT NOT NULL," +
            NewsContract.newsData.COLUMN_URL + " TEXT NOT NULL)";


    private static final String SQL_DELETE_ENTRIES =  "DROP TABLE IF EXISTS " + NewsContract.newsData.TABLE_NAME;

    public NewsSQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
