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

import java.util.ArrayList;
import java.util.List;

public class Angebote extends AppCompatActivity{

    DatabaseHelper helper = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebote);
        greetMe();
    }

    //Funktion, mit der man zu der "Profil" Activity zurückkommt
    public void meinProfil(final View view) {
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }



    public void kaufNetflix(final View view){
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        final int guthaben = helper.sucheGuthaben(newString);

        TextView ntv = (TextView) findViewById(R.id.netflix_preis);
        int netflixp = Integer.parseInt(ntv.getText().toString());
        Log.i("kaufmich", "im ersten knopf");
        if (guthaben >= netflixp) {
            handle_rest(guthaben, netflixp);
        }else{
            planb();
        }

        }


    public void kaufSpotify(final View view){
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        final int guthaben = helper.sucheGuthaben(newString);

        TextView stv = (TextView) findViewById(R.id.spotify_preis);
        int spotifyp = Integer.parseInt(stv.getText().toString());
        Log.i("kaufmich", "im zweiten knopf");
        if (guthaben >= spotifyp) {
            handle_rest(guthaben, spotifyp);
        }else{
            planb();
        }
}

    public void kaufMcFit(final View view){

        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        final int guthaben = helper.sucheGuthaben(newString);

        TextView mctv = (TextView) findViewById(R.id.mcfit_preis);
        int mcfitp = Integer.parseInt(mctv.getText().toString());
        Log.i("kaufmich", "im dritten knopf");
        if (guthaben >= mcfitp) {
            handle_rest(guthaben, mcfitp);
        }else{
            Log.i("kaufmcfit","plan b");
            planb();
        }

    }


    public void planb(){
        String[] answers = {"Zurück zu den Angeboten"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Du hast nicht genung Geld");
        builder.setItems(answers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void leiteProfil(){
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }

    public void handle_rest(final int guthaben, final int preis) {
        Log.i("handle rest", "vor antworten");
        String[] answers = {"yes", "no"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bist du dir sicher");
        builder.setItems(answers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                if (which == 0) {
                    Bundle extras = getIntent().getExtras();
                    String newString = extras.getString("user_name");
                    machWasZuTunIst(guthaben, preis, newString);
                } else {
                   dialog.dismiss();
                }
            }
        });
        builder.show();
}

    //Funktion, die das Guthaben in der Datenbank updatet.
    public void machWasZuTunIst(int guthaben, int preis, String name){
        int neues_guthaben = guthaben - preis;
        String name1 = "'"+ name + "'";
        helper.updateGuthabenDB(neues_guthaben, name1);
        String kauf;

        TextView ntv = (TextView) findViewById(R.id.netflix_preis);
        int netflixp = Integer.parseInt(ntv.getText().toString());

        TextView stv = (TextView) findViewById(R.id.spotify_preis);
        int spotifyp = Integer.parseInt(stv.getText().toString());

        if(preis == netflixp){
          kauf ="'Netflix'";
        }if(preis == spotifyp){
            kauf="'Spotify'";
        }else{
            kauf = "'Mcfit'";
        }
        helper.updateKaeufe(name1, kauf);
    }

    public String greetMe(){
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        TextView tv_name = (TextView) findViewById(R.id.textView);
        try {
            int guthaben = helper.sucheGuthaben(newString);
            tv_name.setText("Die Angebote gibt es für dich, " + newString + " und das ist dein Guthaben: " + guthaben + " Euro");
        }catch(Exception e){
            Log.d("Fehler", "Kein Guthaben in DB");
        }
        return newString;
    }



}
