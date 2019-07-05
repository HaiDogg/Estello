package com.example.hdgg.estello;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Profil extends AppCompatActivity {

    Button btnaufladen;
    Button btnviewartikel;
    DatabaseHelper helper = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        btnaufladen = (Button) findViewById(R.id.lad_mich_auf_id);
        btnviewartikel = (Button) findViewById(R.id.kauf_anzeigen);
        welcherName();
    }

    /*
     * Methode erstellt ein Bundle extras, um den Intent zu kriegen und deren Extras zu bekommen.
     * Ein neuer String wird erstellt, der über einen Schlüssel auf die Extras zugreifen kann.
     * Danach wird mit einer Textview der Nutzer mit dem Namen begrüßt.
     * Der String wird für Wiederverwendung zurückgegeben.
     *
     * @return newString
     */

    public String welcherName() {
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        TextView tv_name = (TextView) findViewById(R.id.textView3);
        tv_name.setText("Hallo, " + newString);
        return newString;
    }


    /*
     * Methode sucht die bisherigen Käufe einer Person mithilfe des Namens, der mit der Methode
     * "welcherName" gefunden wird. Danach wird die Methode "sucheKauf" aufgerufen, und gibt die
     * Käufe als Cursor Objekt zurück. Danach wird ein neuer String Buffer erstellt und der Eintrag aus
     * der vierten Spalte wird eingetragen. Danach wird der Buffer zu einem String umgewandelt und
     * und als TextView ausgegeben.
     * @param view
     */

    public void zeigKaufean(final View view) {
        btnviewartikel.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String name = welcherName();
                        name = "'" + name + "'";

                        Cursor res = helper.sucheKaufe(name);

                        StringBuffer buffer = new StringBuffer();
                        if (res.moveToFirst()) {
                            buffer.append(res.getString(3));
                            String Artikel = buffer.toString();
                            TextView tv = (TextView) findViewById(R.id.kaufe_id);
                            tv.setText(Artikel);
                        }
                    }
                }
        );
    }

    /*
     * Methode, die mithilfe von Intent zu der "Angebote" Seite geht und den Namen übergibt mit Extras
     * @param view
     */

    public void gehZuAngebote(final View view) {

        String newString = welcherName();
        Intent i = new Intent(Profil.this, Angebote.class);
        i.putExtra("user_name", newString);
        startActivity(i);
    }

    /*
     * Methode, die mithilfe von Intent zu der "GuthabenAufladen" Seite geht und den Namen übergibt mit Extras
     * @param view
     */

    public void aufladen(final View view) {
        String newString = welcherName();
        Intent i = new Intent(Profil.this, GuthabenAufladen.class);
        i.putExtra("user_name", newString);
        startActivity(i);
    }
}