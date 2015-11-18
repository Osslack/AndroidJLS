package com.example.simon.photojsl;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 27.10.15.
 */
public class ListEntry {
    private Bitmap mImage;
    private String mTitle;
    private String mDate;
    private Uri mSource;
    private static List<ListEntry> mListEntries = new ArrayList<ListEntry>();
    public ListEntry(Bitmap image,String title,String date){
        mImage = image;
        mTitle = title;
        mDate = date;

        mListEntries.add(this);
    }

    public Bitmap getImage() {
        return mImage;
    }

    public String getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public Uri getSource(){
        return mSource;
    }
    public static List<ListEntry>  getAllListEntrys(){
        return mListEntries;

    }
}
