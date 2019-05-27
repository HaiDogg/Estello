package com.example.hdgg.estello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "slicko.db";


    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "user_name_";
    public static final String COLUMN_PASS = "user_pass";
    public static final String COLUMN_GUTHABEN = "guthaben";
    public static final String COLUMN_ARTIKEL = "artikel";


    SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }
    private static final String SQL_CREATE_USER = "create table "+TABLE_NAME+" (user_name_ text not null, user_pass text not null, guthaben INTEGER DEFAULT 10, artikel TEXT );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        if(newVersion>oldVersion) {
            Log.i("Neu", "Updatet Datenbank");
            //db.execSQL("ALTER TABLE user ADD COLUMN"+ COLUMN_GUTHABEN+" INTEGER DEFAULT 10");
            //db.execSQL("ALTER TABLE user ADD COLUMN"+ COLUMN_KAEUFE+" TEXT");
        }
        onCreate(db);
    }


    public Cursor getAllData(){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * FROM "+ TABLE_NAME, null);
        return res;
    }


    public void updateGuthabenDB(int neues_guthaben, String benutzer_name) {
        db = this.getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_NAME+ " SET "+ COLUMN_GUTHABEN+ " = "+neues_guthaben+" WHERE "+ COLUMN_NAME+" = "+ benutzer_name;
        db.execSQL(strSQL);
        db.close();
        }

    public void updateKaeufe( String benutzer_name, String kauf) {
        db = this.getWritableDatabase();
        Log.i("updateKaufe","nach get writable");
        String strabfrage= "SELECT * from "+ TABLE_NAME+" where "+COLUMN_NAME+" = " + benutzer_name;
        Log.i("updateKaufe","nach abfrage");
        Cursor res = db.rawQuery(strabfrage, null);
        Log.i("updateKaufe","nach raw query");
        //wenn es schon was drin gibt dann ja sonst nein
        String bishar ="";
        String newarticles;
        if(res.moveToFirst() && res.getCount() >= 1) {
            do {
                bishar = res.getString(3);

            } while (res.moveToNext());
        }
            Log.i("updateKaufe","nach umwandlung in bishar");
        if (bishar != null){
            kauf = kauf.substring(1,kauf.length()-1);
             newarticles = "'" + bishar + " , " + kauf +"'" ;
        }else{
            newarticles= kauf;
        }

        String strSQL = "UPDATE " + TABLE_NAME+ " SET "+ COLUMN_ARTIKEL+ "  = " +newarticles+" WHERE "+ COLUMN_NAME+" = "+ benutzer_name + ";";
        Log.i("updateKaufe","vor exec");
        db.execSQL(strSQL);
        Log.i("updateKaufe","nach exec");
        db.close();
    }

    public int sucheGuthaben(String newString) {
        db = this.getReadableDatabase();
        String abfrage = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(abfrage, null);
        String a;
        int guthaben=5;
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

    public Cursor sucheKaufe(String string){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * FROM "+ TABLE_NAME + " where "+ COLUMN_NAME +" = " + string, null);
        return res;
    }
}
