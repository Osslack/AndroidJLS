package com.example.simon.photojsl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int PHOTO_REQUEST = 1;
    static final SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
    static final String preference_file_key = "Jendrik_Simon_Louisa_Preference_File_1337";
    static final String key_pic_number = "JSL_PIC_NUMBER";
    static public int pic_number = 0;
    private RecyclerView mRecyclerView;
    private ViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private static File mCurrentPhotoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mContext = getApplicationContext();
        mAdapter = new ViewAdapter(Model.load_entries(mContext));
        mRecyclerView.setAdapter(mAdapter);
        sharedPref = mContext.getSharedPreferences(preference_file_key, mContext.MODE_PRIVATE);
        editor = sharedPref.edit();
        pic_number = sharedPref.getInt(key_pic_number, 0);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.resolveActivity(getPackageManager());
                mCurrentPhotoFile = new File(mContext.getExternalFilesDir(""), "Picture" + pic_number +".jpg");
                Uri pictureUri = Uri.fromFile(mCurrentPhotoFile);
                String pathFile = mCurrentPhotoFile.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
                startActivityForResult(intent, PHOTO_REQUEST);
                //ImageView iv = (ImageView) findViewById(R.id.imageView2);
                //iv.setImageURI(Uri.fromFile(mCurrentPhotoFile));


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public ViewAdapter getViewAdapter(){
        return mAdapter;
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {

                /*if(!mCurrentPhotoFile.exists()){
                    mCurrentPhotoFile.createNewFile();
                }*/
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                final String file = mCurrentPhotoFile.getAbsolutePath();
                BitmapFactory.decodeFile(file, options);
                options.inSampleSize = 6;
                options.inJustDecodeBounds = false;
                Bitmap thumbnail = BitmapFactory.decodeFile(file);


                //Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                //File thumbFile = new File(mContext.getFilesDir(),"Thumb" + pic_number);
                //FileOutputStream fo = new FileOutputStream(mCurrentPhotoFile);
                //if(thumbnail != null){
                //    thumbnail.compress(Bitmap.CompressFormat.JPEG,85,fo);
                //}
                ListEntry LE = new ListEntry(thumbnail,mCurrentPhotoFile.getName(),df.format(new Date()).toString());
                editor.putString(mCurrentPhotoFile.getName(), df.format(new Date()).toString());
                editor.apply();
                mAdapter.addData(LE);
                ++pic_number;
                editor.putInt(key_pic_number, pic_number);
                editor.apply();

            }
            //catch (FileNotFoundException e){
            //    e.printStackTrace();
            //}
            //catch (IOException e){
            //    e.printStackTrace();
            //}

            /*try {
                File picture = new File(mCurrentPhotoFile);
                //Uri pathToPic = data.getData();
                //if (pathToPic != null) {
                
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoFile);
                File file = new File(mContext.getFilesDir(), "Thumb" + pic_number);
                FileOutputStream fOut = new FileOutputStream(file);
                if(bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    Toast.makeText(getApplicationContext(), "Picture" + pic_number, Toast.LENGTH_SHORT).show();
                }
                ListEntry LE;
                LE = new ListEntry(bitmap, file.getName(), df.format(new Date()));
                editor.putString(file.getName(), df.format(new Date()).toString());
                editor.apply();
                fOut.flush();
                fOut.close();
                mAdapter.addData(LE);
                ++pic_number;
                editor.putInt(key_pic_number, pic_number);
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }*/
        }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
