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

public class Angebote extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    Profil profil = new Profil();
    private String benutzer_name; // TODO den müssen wir holen


    // hier kommen die Kategorien rein

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebote);
    }

    public void meinProfil(final View view){
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }

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
    public void machWasZuTunIst(double guthaben, double preis){
        double neues_guthaben = 0;
        neues_guthaben = guthaben - preis;
        guthaben = neues_guthaben;
        //Datenbank updaten
        helper.updateDB(guthaben, benutzer_name);
        TextView angebot_name = (TextView) findViewById(R.id.name_angebot_eins);
        String a_name = angebot_name.getText().toString();
        profil.updateListe(a_name);
    }
}
