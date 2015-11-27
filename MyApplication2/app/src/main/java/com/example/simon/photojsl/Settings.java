package com.example.simon.photojsl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    EditText textEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textEditor = (EditText) findViewById(R.id.editText);
        textEditor.setText(MainMenu.default_Filename);
        textEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    MainMenu.default_Filename = textEditor.getText().toString();
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(MainMenu.preference_file_key, getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(MainMenu.key_default_filename,MainMenu.default_Filename);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"The new default filename is " + MainMenu.default_Filename , Toast.LENGTH_SHORT).show();
                    handled = true;
                }
                return handled;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}
