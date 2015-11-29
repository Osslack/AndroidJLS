package com.example.simon.photojsl;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;

import static android.support.v4.content.FileProvider.getUriForFile;

public class DetailView extends AppCompatActivity {
    private File m_pic;
    private ImageView m_iV;
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
    public  boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_delete:
                /*if(MainMenu.getAdapter().deleteListEntry(m_pic.getName())){
                    m_pic.delete();
                }*/
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
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, m_pic.getName());
                values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                Uri picUri2 = getUriForFile(getApplicationContext(), "com.mydomain.fileprovider", m_pic);
                //values.put(MediaStore.MediaColumns.DATA, picUri2.getPath() );
                grantUriPermission(MediaStore.Images.Media,picUri2,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
