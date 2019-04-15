package com.example.hdgg.estello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    //Version, DBname, tablenames, columnnames
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "slicko.db";
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "user_name";
    public static final String COLUMN_PASS = "user_pass";
    //Guthaben
    public static final String COLUMN_GUTHABEN = "guthaben";


    //Objekt Datenbank
    SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context,"PLATZHALTER_DATENBANK", null, 1);
        Log.d(LOG_TAG,"Helperklasse hat die Datenbank: " +getDatabaseName()+ "erzeugt.");


    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;
        final String SQL_CREATE_USER = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME + "TEXT PRIMARY KEY, "
                + COLUMN_PASS + "TEXT," + COLUMN_GUTHABEN+ "FLOAT DEFAULT 10.00)";

    }

    public void fuegKontaktEin(Benutzer b ){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,b.getBenutzer_name());
        values.put(COLUMN_PASS,b.getBenutzer_pass());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public String suchePasswort(String str_name){
        db = this.getReadableDatabase();
        String abfrage ="select * from "+ TABLE_NAME;
        Cursor cursor= db.rawQuery(abfrage, null);
        String a, b;
        b = "Nicht auffindbar";
        if (cursor.moveToFirst()){
            do{
                a = cursor.getString(1);
                if(a.equals(str_name)){
                    b = cursor.getString(2);
                    break;
                }
            }while(cursor.moveToNext());
        }return b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
