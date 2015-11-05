package com.example.simon.photojsl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by simon on 05.11.15.
 */
public class Model {
    public static ArrayList<ListEntry> load_entries(Context context){
        ArrayList<ListEntry> listEntries = new ArrayList<ListEntry>();
        File folder = context.getFilesDir();
        File[] files = folder.listFiles();
        for(File  a : files){
            try {
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(a));
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                ListEntry LE;
                LE = new ListEntry(b, a.getName(), df.format(new Date()));
                listEntries.add(LE);
            }
            catch (FileNotFoundException e){
                return null;
            }
        }
        return listEntries;
    }
}
