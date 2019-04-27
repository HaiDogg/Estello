package com.example.hdgg.estello;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    Profil profil = new Profil();
    private String benutzer_name; // TODO den müssen wir holen


    // hier kommen die Kategorien rein

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebote);
    }


    //Alle Angebote holen:
    //private List <Listing> angebotsliste;
    //angebotsliste = helper.getAlleListings();



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
    public void kaufMich(final double guthaben, final double preis){
        if (guthaben >= preis){
            String[] answers = {"yes", "no"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bist du dir sicher");
            builder.setItems(answers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on colors[which]
                    if(which == 0){
                        machWasZuTunIst(guthaben,preis);
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
            builder.setTitle("Bist du dir sicher");
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
    public void machWasZuTunIst(double guthaben, double preis){
        double neues_guthaben = 0;
        neues_guthaben = guthaben - preis;
        guthaben = neues_guthaben;
        //Datenbank updaten
        helper.updateGuthabenDB(guthaben, benutzer_name);
        TextView angebot_name = (TextView) findViewById(R.id.angebote);//nochmal strings für die Angebote einfuegen
        String a_name = angebot_name.getText().toString();
        profil.updateListe(a_name);
    }
}
