package com.example.hdgg.estello;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private BenutzerDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Benutzer estelle = new Benutzer
                ("Estelle", "estelle@gmail.com", "haido123");
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + estelle.toString());

        dataSource = new BenutzerDataSource(this);


    }


}
