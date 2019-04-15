package com.example.hdgg.estello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Angebote extends AppCompatActivity {

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
            //wenn ja
                //guthaben wird geupdated
                double neues_guthaben = 0;
                neues_guthaben = guthaben - preis;
                guthaben = neues_guthaben;
                //TODO Datenbank updaten
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
