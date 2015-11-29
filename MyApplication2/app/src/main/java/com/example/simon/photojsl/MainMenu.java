package com.example.simon.photojsl;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int PHOTO_REQUEST = 1;
    static final int RESULT_LOAD_IMAGE = 2;
    static final int RESULT_VIEW_IMAGE = 3;
    static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
    private static final SimpleDateFormat dfParser = new SimpleDateFormat("yyyy:MM:dd kk:mm:ss");
    static final String preference_file_key = "Jendrik_Simon_Louisa_Preference_File_1337";
    static final String key_pic_number = "JSL_PIC_NUMBER";
    static final String key_default_filename = "JSL_DEFFILENAME";
    static public int pic_number = 0;
    static final String file_extension = ".png";
    private RecyclerView mRecyclerView;
    private ViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private static String mPathToFile;
    private static String mFilename;
    public static String default_Filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get the RecyclerView up and running
        mRecyclerView = (RecyclerView) findViewById(R.id.view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mContext = getApplicationContext();
        mAdapter = new ViewAdapter(Model.load_entries(mContext));
        mRecyclerView.setAdapter(mAdapter);
        //Get the Shared Preferences etc. and load the pic_number and default_filename
        sharedPref = mContext.getSharedPreferences(preference_file_key, mContext.MODE_PRIVATE);
        editor = sharedPref.edit();
        pic_number = sharedPref.getInt(key_pic_number, 0);
        default_Filename = sharedPref.getString(key_default_filename,"Picture");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.resolveActivity(getPackageManager());

                File picture = newImageFile();
                mFilename = picture.getName();
                mPathToFile = picture.getAbsolutePath();

                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(picture));
                startActivityForResult(intent, PHOTO_REQUEST);
            }
        });

    }

    public ViewAdapter getAdapter(){
        return mAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            addImage(mPathToFile, mFilename,df.format(new Date()).toString());
            }
        else if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();

            File dest = newImageFile();
            copyFile(selectedImage, dest);
            String date = getPhotoCapturedDate(dest.getAbsolutePath());
            if(date == null) {
                date = df.format(new Date()).toString();
                Toast.makeText(getApplicationContext(), "No date found, current date used instead", Toast.LENGTH_SHORT).show();
            }//ads
            addImage(dest.getAbsolutePath(), dest.getName(), date);
        }
        else if(requestCode == RESULT_VIEW_IMAGE && resultCode == RESULT_CANCELED){
            if(data != null) {
                String name = data.getAction();
                mAdapter.deleteListEntry(name);
            }
        }
    }
    public void addImage(String pathToFile,String filename,String date){
        Bitmap picture = Model.loadThumbFromFile(pathToFile);
        ListEntry LE = new ListEntry(picture,filename,date);
        saveDate(filename,date);
        mAdapter.addData(LE);
        increasePicNumber();
    }
    public void increasePicNumber(){
        ++pic_number;
        editor.putInt(key_pic_number, pic_number);
        editor.apply();
    }
    public void saveDate(String filename, String date){
        editor.putString(filename, date);
        editor.apply();
    }
    public File newImageFile(){
        return new File(mContext.getExternalFilesDir(""), default_Filename + pic_number + file_extension);
    }

    private void copyFile(Uri source,File dest){
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            InputStream isSRC = mContext.getContentResolver().openInputStream(source);
            in = new BufferedInputStream(isSRC);
            out = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            in.read(buffer);
            do {
                out.write(buffer);
            } while (in.read(buffer) != -1);
        } catch (FileNotFoundException e) {
            //TODO
            e.printStackTrace();
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e){}
        }
    }
    private String getPhotoCapturedDate(String filePath){

        if(filePath == null){
            return null;
        }
        //String capturedDate = null;
        try {
            ExifInterface exif = new ExifInterface(filePath);
            if(exif != null){

                return df.format(dfParser.parse(exif.getAttribute(ExifInterface.TAG_DATETIME))).toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    @Override
    public void onBackPressed() {
            super.onBackPressed();
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
        switch(id){
            case R.id.action_gallery  : Intent i = new Intent(
                                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                                        return true;
            case R.id.action_settings : Intent intent = new Intent(mContext,Settings.class);
                                        startActivity(intent);
                                        return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        return true;
    }



}
