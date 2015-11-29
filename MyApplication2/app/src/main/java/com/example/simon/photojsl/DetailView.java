package com.example.simon.photojsl;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

import static android.support.v4.content.FileProvider.getUriForFile;

public class DetailView extends AppCompatActivity {
    private File m_pic;
    private ImageView m_iV;
    final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("Filename");
        m_pic = new File(getApplicationContext().getExternalFilesDir(null), filename);
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, m_pic.getName());
                    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                    values.put(MediaStore.MediaColumns.DATA, m_pic.getAbsolutePath());

//                    Uri picUri2 = getUriForFile(getApplicationContext(), "com.mydomain.fileprovider", m_pic);
                    //values.put(MediaStore.MediaColumns.DATA, picUri2.getPath() );
//                grantUriPermission(MediaStore.Images.Media,picUri2,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                }
                return;
            }
        }
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_delete:
                m_pic.delete();
                getIntent().setAction(m_pic.getName());
                setResult(Activity.RESULT_CANCELED, getIntent());
                finish();
                break;
            case R.id.action_send:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_SUBJECT,m_pic.getName());
                i.putExtra(android.content.Intent.EXTRA_TEXT, "powered by JLS Software");
                Uri picUri = getUriForFile(getApplicationContext(), "com.mydomain.fileprovider", m_pic);
                i.putExtra(Intent.EXTRA_STREAM, picUri);
                i.setType("image/png");
                startActivity(Intent.createChooser(i,"Choose an application to share your picture"));
                break;
            case R.id.action_save:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
