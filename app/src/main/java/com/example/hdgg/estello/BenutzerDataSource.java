package com.example.hdgg.estello;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BenutzerDataSource {


    private static final String LOG_TAG = BenutzerDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public BenutzerDataSource(Context context){
        Log.d(LOG_TAG, "unsere Datasource-klasse erzeugt den dbHelper. ");
        dbHelper = new DatabaseHelper(context);
    }
}
