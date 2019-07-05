package com.example.hdgg.estello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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


    private static final String SQL_CREATE_USER = "create table "+TABLE_NAME+" (user_name_"+
            " text not null, user_pass text not null, guthaben INTEGER DEFAULT 10, artikel text );";

    private static final String SQL_CREATE_ARTIKEL = "create table "+ TABLE_NAME_2 + " (artikel_name text not null,"+
            " artikel_preis INTEGER);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_ARTIKEL);
        this.db = db;
    }


    /*
     * Die Methode bekommt den Benutzer b übergeben mithilfe von Contentvalues. Damit werden die einzelnen
     * Attribute in die jeweiligen Spalten eingefügt und in die Tabelle eingefügt. Nach dem Öffnen wird die
     * Datenbankanbindung wieder geschlossen.
     * @param b
     */

    public void fuegKontaktEin(Benutzer b ){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,b.getBenutzer_name());
        values.put(COLUMN_PASS,b.getBenutzer_pass());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }


    /*
     *Funktion fragt alle Einträge der Tabelle "user" ab und erstellt einen Cursor.
     *Es erstellt String a und b, wobei b auf "Nicht auffindbar" gesetzt wird und a jeweils den
     *Wert der ersten Spalte in der "user" Tabelle einnimmt. Sobald der Wert mit "str_name" übereinstimmt,
     *wird "b" mit dem Wert aus der zweiten Spalte in der gleichen Zeile überschrieben.
     *Die Funktion gibt das b zurück
     *
     *@param str_name
     *@return b
     */

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

    /**public String erstellePasswort(String passwort){

        MessageDigest md ;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(passwort.getBytes(Charset.forName("US-ASCII")),0,passwort.length());
            byte[] magnitude = md.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;

        }
        catch (java.security.NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;

        **/



    public static String erstellePasswort(String password) throws NoSuchAlgorithmException,
                InvalidKeySpecException {


            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

                // bytes to hex
                StringBuilder sb = new StringBuilder();
                for (byte b : hashInBytes) {
                    sb.append(String.format("%02x", b));
                    password = sb.toString();
                }
            }
                catch(java.security.NoSuchAlgorithmException e){
                    e.printStackTrace();
                }
            return password;

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
        String strSQL = "UPDATE " + TABLE_NAME+ " SET "+ COLUMN_ARTIKEL+ "  = " +newarticles+" WHERE "+
                COLUMN_NAME+" = "+ benutzer_name + ";";
        db.execSQL(strSQL);
        db.close();
    }

    /*
     * Methode kriegt eine lesbare Datenbank und führt eine SQL Query aus. Hierbei wird das Guthaben
     * aus der Tabelle user gezogen, wo der Name gleich dem übergebenen Namen ist.
     *
     * @param newString
     * @return res
     */

    public Cursor sucheGuthaben(String newString) {
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " +COLUMN_GUTHABEN + " FROM "+
                TABLE_NAME + " where "+ COLUMN_NAME +
                " = " + newString, null);
        return res;
    }

    /*
     * Methode kriegt eine lesbare Datenbank und führt eine SQL Query aus. Hierbei werden die Käufe
     * aus der Tabelle user gezogen, wo der Name gleich dem übergebenen Namen ist.
     *
     * @param newString
     * @return res
     */

    public Cursor sucheKaufe(String string){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * FROM "+ TABLE_NAME + " where "+ COLUMN_NAME +
                " = " + string, null);
        return res;
    }


    /*
     * Methode  erstellt  Artikel indem es neue Objekte der Klasse Artikel erstellt und mit Werten füllt.
     * Mit diesen Objekten wird die Methode "addArtikel" aufgerufen.
     */

    public void artikelErstellen(){
        Artikel a1 = new Artikel("Netflix", 10);
        addArtikel(a1);
        Artikel a2 = new Artikel("Spotify", 5);
        addArtikel(a2);
        Artikel a3 = new Artikel("McFit", 20);
        addArtikel(a3);
    }

    /*
     * Methode öffnet eine schreibbare Datenbank und erstellt ein Objekt cv von ContentValues.
     * Das Objekt benutzt die Methode put, um die jeweiligen Attribute in die Spalten einzutragen.
     * Danach wird das cv in die Datenbank eingefügt und danach geschlossen.
     *
     * @param artikel
     */


    private void addArtikel(Artikel artikel){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ARTIKEL_NAME,artikel.getArtikel_name());
        cv.put(COLUMN_ARTIKEL_PREIS, artikel.getArtikel_preis());
        db.insert(TABLE_NAME_2, null, cv);
        db.close();
    }

    /*
     * Methode erstellt eine Arraylist "artikellist" und öffnet eine lesbare Datenbank.
     * In den Cursor c wird das Ergebnis einer SQL Query gespeichert, welche alle Daten von der Tabelle
     * "artikel" enthält. Dann wird mithilfe einer Schleife jeweils der Name und der Preis aus der
     * jeweiligen Spalte gezogen und der ganze artikel zur Artikelliste hinzugefügt.
     * Sobald alle Einträge eingespeichert sind, wird die Datenbank geschlossen und die Artikelliste
     * zurückgegeben.
     *
     * @return artikelList
     */


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
