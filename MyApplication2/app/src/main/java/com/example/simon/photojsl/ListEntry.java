package com.example.simon.photojsl;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by simon on 27.10.15.
 */

/**
 * Data structure used to store an image and the corresponding date and title.
 */
public class ListEntry {
    private Bitmap mImage;
    private String mTitle;
    private String mDate;
    private Uri mSource;
    private static ArrayList<ListEntry> mListEntries = new ArrayList<ListEntry>();

    public ListEntry(Bitmap image,String title,String date){
        mImage = image;
        mTitle = title;
        mDate = date;

        mListEntries.add(this);
    }
    public ListEntry(String title,String date){
        mTitle = title;
        mDate = date;
        mImage = null;


    }
    public void addBitmap (Bitmap bitmap){
        if(mImage == null){
            mImage = bitmap;
            mListEntries.add(this);
        }
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
    public static ArrayList<ListEntry>  getAllListEntrys(){
        return mListEntries;
    }
}
