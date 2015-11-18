package com.example.simon.photojsl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("Titel");
        Context context = getApplicationContext();

            //FileInputStream fIn = openFileInput(filename);
            File pic = new File(getApplicationContext().getExternalFilesDir(""),filename);
            Bitmap thumbnail = BitmapFactory.decodeFile(pic.getAbsolutePath());
            //Bitmap picture = BitmapFactory.decodeStream(fIn);
            ImageView iV = (ImageView) findViewById(R.id.imageView_Detail);
            if(thumbnail != null) {
                iV.setImageBitmap(thumbnail);
            }




    }
}
