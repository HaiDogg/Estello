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
import android.widget.Toast;

public class Angebote extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);


    // hier kommen die Kategorien rein

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebote);
    }

    public void meinProfil(final View view){
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }

    public void kaufMich(double preis, double guthaben){
        if (guthaben >= preis){
            //TODO Toast bist du dir sicher?
            String[] answers = {"yes", "no"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bist du dir sicher");
            builder.setItems(answers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on colors[which]
                }
            });
            builder.show();
            //wenn ja
                //guthaben wird geupdated
                double neues_guthaben = 0;
                neues_guthaben = guthaben - preis;
                guthaben = neues_guthaben;
                //TODO Datenbank updaten
                helper.updateDB(guthaben);
                //TODO Im Profil wird der Kauf in die Liste hinzugefügt

                //...

        }else{
            //Toast: du kannst es dir nicht leisten, möchtest du a) in dein profil aufladen, b) zu angeboten zurück
            if(1==2){
            Intent i = new Intent(Angebote.this, Profil.class);
            startActivity(i);
        }else{

                Intent i = new Intent(Angebote.this,Angebote.class);
                startActivity(i);
            }

    }
}
}
