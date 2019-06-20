package com.example.hdgg.estello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "slicko.db";


    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "user_name_";
    public static final String COLUMN_PASS = "user_pass";
    public static final String COLUMN_GUTHABEN = "guthaben";
    public static final String COLUMN_ARTIKEL = "artikel";

    public static final String TABLE_NAME_2 = "artikel";
    public static final String COLUMN_ARTIKEL_NAME = "artikel_name";
    public static final String COLUMN_ARTIKEL_PREIS = "artikel_preis";



    SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }
    private static final String SQL_CREATE_USER = "create table "+TABLE_NAME+" (user_name_ text not null, user_pass text not null,"+
            " guthaben INTEGER DEFAULT 10, artikel TEXT );";

    private static final String SQL_CREATE_ARTIKEL = "create table "+ TABLE_NAME_2 + " (artikel_name text not null,"+
            " artikel_preis INTEGER);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_ARTIKEL);
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
                a = cursor.getString(0);
                if(a.equals(str_name)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }return b;
    }


    public void laddbauf(String name, int neuesguthaben){
        db = this.getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_NAME+ " SET "+ COLUMN_GUTHABEN+ " = "+neuesguthaben+" WHERE "+ COLUMN_NAME+" = "+ name;
        db.execSQL(strSQL);
        db.close();
    }

    public void updateGuthabenDB(int neues_guthaben, String benutzer_name) {
        db = this.getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_NAME+ " SET "+ COLUMN_GUTHABEN+ " = "+neues_guthaben+" WHERE "+ COLUMN_NAME+" = "+ benutzer_name;
        db.execSQL(strSQL);
        db.close();
        }

    public void updateKaeufe( String benutzer_name, String kauf) {
        db = this.getWritableDatabase();

        String strabfrage= "SELECT * from "+ TABLE_NAME+" where "+COLUMN_NAME+" = " + benutzer_name;

        Cursor res = db.rawQuery(strabfrage, null);

        String bishar ="";
        String newarticles;
        if(res.moveToFirst() && res.getCount() >= 1) {
            do {
                bishar = res.getString(3);

            } while (res.moveToNext());
        }

        if (bishar != null){
            kauf = kauf.substring(1,kauf.length()-1);
             newarticles = "'" + bishar + " , " + kauf +"'" ;
        }else{
            newarticles= kauf;
        }
        String strSQL = "UPDATE " + TABLE_NAME+ " SET "+ COLUMN_ARTIKEL+ "  = " +newarticles+" WHERE "+ COLUMN_NAME+" = "+ benutzer_name + ";";
        db.execSQL(strSQL);
        db.close();
    }

    public Cursor sucheGuthaben(String newString) {
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " +COLUMN_GUTHABEN + " FROM "+ TABLE_NAME + " where "+ COLUMN_NAME +" = " + newString, null);
        return res;
    }

    public Cursor sucheKaufe(String string){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * FROM "+ TABLE_NAME + " where "+ COLUMN_NAME +" = " + string, null);
        return res;
    }



    public void artikelErstellen(){
        Artikel a1 = new Artikel("Netflix", 10);
        addArtikel(a1);
        Artikel a2 = new Artikel("Spotify", 5);
        addArtikel(a2);
        Artikel a3 = new Artikel("McFit", 20);
        addArtikel(a3);
    }

    private void addArtikel(Artikel artikel){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ARTIKEL_NAME,artikel.getArtikel_name());
        cv.put(COLUMN_ARTIKEL_PREIS, artikel.getArtikel_preis());
        db.insert(TABLE_NAME_2, null, cv);
        db.close();
    }


    public List<Artikel> getAlleArtikel(){
        List<Artikel> artikelList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from "+ TABLE_NAME_2, null);
        if(c.moveToFirst()){
            do{
                Artikel artikel = new Artikel();
                artikel.setArtikel_name(c.getString(c.getColumnIndex(COLUMN_ARTIKEL_NAME)));
                artikel.setArtikel_preis(c.getInt(c.getColumnIndex(COLUMN_ARTIKEL_PREIS)));
                artikelList.add(artikel);
            }while(c.moveToNext());
        }
        c.close();
        return artikelList;
    }


    public Cursor getAllData(){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * FROM "+ TABLE_NAME_2, null);
        return res;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_2);
        /*if(newVersion>oldVersion) {
            Log.i("Neu", "Updatet Datenbank");
            //db.execSQL("ALTER TABLE user ADD COLUMN"+ COLUMN_GUTHABEN+" INTEGER DEFAULT 10");
            //db.execSQL("ALTER TABLE user ADD COLUMN"+ COLUMN_KAEUFE+" TEXT");
        }*/
        onCreate(db);
    }
}
