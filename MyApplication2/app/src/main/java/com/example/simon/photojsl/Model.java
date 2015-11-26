package com.example.simon.photojsl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by simon on 05.11.15.
 */
public class Model {
    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    public static int thumbHeight = 200;
    public static int thumbWidth = 200;

    public static ArrayList<ListEntry> load_entries(Context context) {
        ArrayList<ListEntry> listEntries = new ArrayList<ListEntry>();
        File folder = context.getExternalFilesDir("");
        File[] files = folder.listFiles();
        Date dateConverter = new Date();
        ListEntry LE;
        sharedPref = context.getSharedPreferences(MainMenu.preference_file_key, context.MODE_PRIVATE);
        editor = sharedPref.edit();
        for (File a : files) {
            String pathToFile = a.getAbsolutePath();
            Bitmap thumbnail = loadThumbFromFile(pathToFile);
            LE = new ListEntry(thumbnail, a.getName(), sharedPref.getString(a.getName(), "Kappa"));
            listEntries.add(0, LE);


        }
        return listEntries;
    }

    public static int calculateInSampleSize(    //as found in the google documentation
                                                BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > thumbHeight || width > thumbWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > thumbHeight
                    && (halfWidth / inSampleSize) > thumbWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap loadThumbFromFile(String path){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds = false;
        Bitmap b = BitmapFactory.decodeFile(path, options);
        Bitmap part = Bitmap.createBitmap(b, b.getWidth() / 2 - thumbWidth / 2, b.getHeight() / 2 - thumbHeight / 2, thumbWidth, thumbHeight, null, true);
        return part;
    }
}

