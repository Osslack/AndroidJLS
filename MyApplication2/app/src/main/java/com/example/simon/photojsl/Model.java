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
    public static ArrayList<ListEntry> load_entries(Context context){
        ArrayList<ListEntry> listEntries = new ArrayList<ListEntry>();
        File folder = context.getExternalFilesDir("");
        File[] files = folder.listFiles();
        Date dateConverter = new Date();
        sharedPref = context.getSharedPreferences(MainMenu.preference_file_key, context.MODE_PRIVATE);
        editor = sharedPref.edit();
        for(File  a : files){
                    String pathToFile = a.getAbsolutePath();
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(pathToFile, options);
                    options.inSampleSize = 10;
                    options.inJustDecodeBounds = false;
                    Bitmap b = BitmapFactory.decodeFile(pathToFile,options);

                    //dateConverter = MainMenu.df.parse(sharedPref.getString(a.getName(),""));
                    ListEntry LE;
                    LE = new ListEntry(b, a.getName(), sharedPref.getString(a.getName(), "Kappa"));
                    listEntries.add(0, LE);



        }
        return listEntries;
    }
}
