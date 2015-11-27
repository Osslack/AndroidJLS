package com.example.simon.photojsl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;

public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("Titel");

        //Context context = getApplicationContext();
        File pic = new File(getApplicationContext().getExternalFilesDir(""), filename);
        //BitmapWorkerTask task = new BitmapWorkerTask((ImageView)findViewById(R.id.imageView_Detail));
        //task.execute(path);
        /*final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = Model.calculateInScreenSampleSize(options);
        options.inJustDecodeBounds = false;
        Bitmap b = BitmapFactory.decodeFile(path, options);
        //Bitmap thumbnail = BitmapFactory.decodeFile(pic.getAbsolutePath());*/
        ImageView iV = (ImageView) findViewById(R.id.imageView_Detail);
        if (pic != null) {
            iV.setImageURI(Uri.fromFile(pic));
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
