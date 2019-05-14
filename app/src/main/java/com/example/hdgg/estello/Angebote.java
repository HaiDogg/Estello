package com.example.hdgg.estello;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Angebote extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebote);

        // Hier wird der User Name rausgeholt und begrüßt
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= getIntent().getStringExtra("brauche");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("brauche");
        }
        //TODO funktion die über namen das guthaben aus der db holt
       // holGuthaben(newString);
        TextView tv_name = (TextView) findViewById(R.id.textView);
        tv_name.setText("Die Angebote gibt es für dich, "+ newString );

    }




    public int holGuthaben(String newString){


        int guthaben = helper.welchesGuthaben(newString);
        TextView tv_guthaben = (TextView) findViewById(R.id.textView);
        tv_guthaben.setText( "h"+ guthaben );
        return guthaben;
    }

    //Funktion, mit der man zu der "Profil" Activity zurückkommt
    public void meinProfil(final View view){
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }

    /*
    * Funktion, in der verglichen wird, ob der Kunde genug Guthaben hat, um den Artikel zu kaufen.
    * Wenn dem so ist, wird er mit einem AlertDialog gefragt, ob er sich sicher ist, dass er den Artikel
    * erwerben will. Wenn er sich sicher ist, wird die Funktion "machWasZuTunIst" aufgerufen, welche
    * das Guthaben in der Datenbank updatet. Außerdem wird der Name des Artikels im Profil des Kunden
    * im Bereich "Meine Käufe" angezeigt. Wenn er sich nicht sicher ist, kann er zurück zur Angebotsübersicht gehen.
    * Wenn der Kunde nicht genügend Guthaben hat, hat er die Möglichkeit sich zu seinem Profil leiten
    * zu lassen, wo er den Button "Guthaben aufladen" findet. Ansonsten kann er zurück zur Angebots-
    * übersicht.
    *
    * */

    public void kaufMich(final View view){

        //guthaben von kunde geholt mit dem namen den wir übergeben kriegen

        final double guthaben=0;
        final double preis=0;

        if (guthaben >= preis){
            String[] answers = {"yes", "no"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bist du dir sicher");
            builder.setItems(answers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on colors[which]
                    if(which == 0){
                        machWasZuTunIst(guthaben,preis, "Esti");
                    }
                    else{
                        Intent i = new Intent(Angebote.this,Angebote.class);
                        startActivity(i);
                    }
                }
            });
            builder.show();
        }else{
            String[] answers = {"Guthaben Aufladen", "Zurück zu den Angeboten"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Du hast nicht genung Geld");
            builder.setItems(answers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on colors[which]
                    if (which ==0){
                        Intent i = new Intent(Angebote.this, Profil.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(Angebote.this,Angebote.class);
                        startActivity(i);
                    }
                }
            });
            builder.show();
    }
}

    //Funktion, die das Guthaben in der Datenbank updatet.
    public void machWasZuTunIst(double guthaben, double preis, String name){
        double neues_guthaben = 0;
        neues_guthaben = guthaben - preis;
        guthaben = neues_guthaben;

        ContentValues contentvalues = new ContentValues();
        //SQLiteDatabase db = getDatabasePath(DatabaseHelper.COLUMN_NAME);
        //db.update("slicko.db", contentvalues, "COLUMN_GUTHABEN = ?",new double[]{neues_guthaben});
        //

        //Datenbank updaten
        //helper.updateGuthabenDB(guthaben, benutzer_name);
        TextView angebot_name = (TextView) findViewById(R.id.angebote);//nochmal strings für die Angebote einfuegen
        String a_name = angebot_name.getText().toString();

       // profil.updateListe(a_name);
        // intent mit dem der name vom kauf übergeben wird.
        String kaufName = "Netflix";
        Intent i = new Intent(Angebote.this, Profil.class);
        i.putExtra("kauf", kaufName);
        startActivity(i);
    }
}
