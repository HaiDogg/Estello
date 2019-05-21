package com.example.hdgg.estello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{


    //Version, DBname, tablenames, columnnames
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "slicko.db";


    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "user_name_";
    public static final String COLUMN_PASS = "user_pass";
    public static final String COLUMN_GUTHABEN = "guthaben";

    public static final String TABLE_LISTING = "listings";
    public static final String COLUMN_LISTING_NAME = "listingname";
    public static final String COLUMN_LISTING_PREIS = "listingpreis";




    //Objekt Datenbank
    SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, 1);
    }


    private static final String SQL_CREATE_USER = "create table user (user_name_ text not null, user_pass text not null, guthaben int not null default 10);";


    //final String SQL_CREATE_LISTING = "CREATE TABLE " + TABLE_LISTING + "(" + COLUMN_LISTING_NAME+ "TEXT PRIMARY KEY, "
    //        + COLUMN_LISTING_PREIS + "FLOAT)";
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USER);
        //db.execSQL(SQL_CREATE_LISTING);
        this.db = db;

    }

    public void fuegKontaktEin(Benutzer b ){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,b.getBenutzer_name());
        values.put(COLUMN_PASS,b.getBenutzer_pass());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int welchesGuthaben(String newString) {
       db = this.getReadableDatabase();
        String abfrage = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(abfrage, null);
        String a;
        int guthaben=0;
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(newString)) {
                    guthaben = cursor.getInt(2);
                    break;
                }
            } while (cursor.moveToNext());
        }return guthaben;
    }
    public String suchePasswort(String str_name){
        db = this.getReadableDatabase();
        String abfrage ="SELECT * FROM "+ TABLE_NAME;
        Cursor cursor= db.rawQuery(abfrage, null);
        String a, b;
        b = "Nicht auffindbar";
        if (cursor.moveToFirst()){
            do{
                a = cursor.getString(0); // Hier aufmerksam sein, ob das anders ist wegen fehlender ID , in letzten Programm 1 und 2
                if(a.equals(str_name)){
                    b = cursor.getString(1); // Same
                    break;
                }
            }while(cursor.moveToNext());
        }return b;
    }

    public void updateGuthabenDB(double guthaben,
                                 double preis,
                                 String benutzer_name){
        db = this.getWritableDatabase();
        //String updateString = "UPDATE " + TABLE_NAME +" SET "+ COLUMN_GUTHABEN+" = "+  guthaben+ "" +
        //        "WHERE "+ COLUMN_NAME+" LIKE "+ benutzer_name;
        //db.execSQL(updateString );


        ContentValues values = new ContentValues();
        values.put(this.COLUMN_GUTHABEN, guthaben - preis);
        db.update("slicko.db", values, this.COLUMN_NAME + "=" + benutzer_name,null);



        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LISTING);

        onCreate(db);
    }
}
