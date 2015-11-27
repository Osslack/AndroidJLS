package com.example.simon.photojsl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DetailView extends AppCompatActivity {
    private File m_pic;
    private ImageView m_iV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("Filename");
        m_pic = new File(getApplicationContext().getExternalFilesDir(""), filename);
        m_iV = (ImageView) findViewById(R.id.imageView_Detail);
        if (m_pic != null) {
            m_iV.setImageURI(Uri.fromFile(m_pic));
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_delete:
                m_pic.delete();
                break;
            case R.id.action_send:
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, m_pic.getName());
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "powered by JLS Software");
                emailIntent.setType("image/png");
                Uri imageUri = null;
                try {
                    imageUri = Uri.parse(m_pic.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(emailIntent, "Send mail"));
                break;
            case R.id.action_save:
                try {
                    Images.Media.insertImage(getContentResolver(), m_pic.getAbsolutePath(),m_pic.getName(), "JLS Picture");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
