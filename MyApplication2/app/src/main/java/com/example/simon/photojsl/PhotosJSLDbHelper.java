package com.example.simon.photojsl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by simon on 27.10.15.
 */
public class PhotosJSLDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE" + PhotosJSLContract.PhotosJSL.TABLE_NAME + "( " +
                    PhotosJSLContract.PhotosJSL._ID + " INTEGER PRIMARY KEY," +
                    PhotosJSLContract.PhotosJSL.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    PhotosJSLContract.PhotosJSL.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    PhotosJSLContract.PhotosJSL.COLUMN_NAME_DATE  + TEXT_TYPE + ")";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PhotosJSLContract.PhotosJSL.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhotosJSL.db";

    public PhotosJSLDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
