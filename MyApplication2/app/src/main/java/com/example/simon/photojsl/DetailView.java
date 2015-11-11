package com.example.simon.photojsl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("Titel");
        Context context = getApplicationContext();
        try {
            FileInputStream fIn = openFileInput(filename);
            Bitmap picture = BitmapFactory.decodeStream(fIn);
            ImageView iV = (ImageView) findViewById(R.id.imageView_Detail);
            if(picture != null) {
                iV.setImageBitmap(picture);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}
