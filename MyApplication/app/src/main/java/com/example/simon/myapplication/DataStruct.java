package com.example.simon.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 27.10.15.
 */
public class DataStruct {
    private String mTitle;
    private String mText;

    public DataStruct(String title,String text) {
        mTitle = title;
        mText = text;
    }
    public String getTitle() {
        return mTitle;
    }
    public String getText() {
        return mText;
    }

    private static int lastElement = 0;

    public static List<DataStruct> createContactsList(int numElements) {
        List<DataStruct> structs = new ArrayList<DataStruct>();

        for (int i = 1; i <= numElements; i++) {
            structs.add(new DataStruct("Person " + ++lastElement, "Kapparino"+ lastElement));
        }

        return structs;
    }
}

