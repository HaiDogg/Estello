package com.example.hdgg.estello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Erstellung der DB
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME ="users.db";

    // Erstellung von der Tabelle user
    private static final String TABLE_NAME ="user";
    private static final String COLUMN_NAME ="userName";
    private static final String COLUMN_PASSWORT ="userPasswort";

    //Tabelle Guthaben
    // TODO ( darf nicht ins minus kommen)
    private static final String TABLE_GUTHABEN ="guthaben";

    //Objekt von SQLiteDatabase erstellen(db)
    SQLiteDatabase db;


    /**
     * In dieser Methode wird ein Hilfsobjekt kreiert, um die Datenbank zu kreieren, oeffnen und zu managen.
     * @param context
     */
    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Kreation der Datenbank mit Hilfe einer SQL Query, die dann in die onCreate Methode eingefügt wird
    private static final String CREATE_TABLE =
            "create table user (userName text not null primary key , userPasswort text not null);";
    private static final String CREATE_TABLE_2 =
            "create table guthaben(guthaben float primary key); ";


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_2);

        this.db = db;
    }


    /**
     * Methode, um einen neuen Kontakt einfuegen
     *@param k
     */
    public void fuegKontaktEin(Kontakt k ){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,k.getUsername());
        values.put(COLUMN_PASSWORT,k.getUpasswort());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**
     * Methode, um eine Username-Passwort Kombination zu pruefen
     * @param str_name
     * @return b
     */
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



    /**
     * Diese Methode dient dazu bei Bedarf die Datenbank zu aktualisieren indem die alten Tabellen
     * geloescht werden. Dies geschieht mithilfe von SQL Statements, die mit execSQL ausgefuehrt
     * werden. Danach wird wieder die onCreate() Methode aufgerufen.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+  TABLE_GUTHABEN);
        onCreate(db);
    }
}
