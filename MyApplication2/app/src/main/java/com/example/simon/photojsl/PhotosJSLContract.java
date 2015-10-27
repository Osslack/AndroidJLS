package com.example.simon.photojsl;

import android.provider.BaseColumns;

/**
 * Created by simon on 27.10.15.
 */
public class PhotosJSLContract {
    public PhotosJSLContract(){

    }
    public static abstract class PhotosJSL implements BaseColumns {
        public static final String TABLE_NAME = "photos";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";




    }

}
