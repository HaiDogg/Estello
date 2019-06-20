package com.example.hdgg.estello;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Angebote extends AppCompatActivity{

    DatabaseHelper helper = new DatabaseHelper(this);
    private List<Artikel> artikel_list;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebote);
        greetMe();

        TextView a1 = (TextView) findViewById(R.id.tv1);
        TextView a2 = (TextView) findViewById(R.id.tv2);
        TextView a3 = (TextView) findViewById(R.id.tv3);

        TextView p1 = (TextView) findViewById(R.id.netflix_preis);
        TextView p2 = (TextView) findViewById(R.id.spotify_preis);
        TextView p3 = (TextView) findViewById(R.id.mcfit_preis);


        artikel_list = helper.getAlleArtikel();
        if(artikel_list.size()==0){
            helper.artikelErstellen();
            artikel_list = helper.getAlleArtikel();
        }

        Cursor res = helper.getAllData();
        if(res.getCount()==0){
            showMessage("Error", "Nothing found");
            return;
        }
        res.moveToFirst();
        a1.setText("" + res.getString(0) );
        p1.setText("" + res.getInt(1) );
        res.moveToNext();
        a2.setText("" + res.getString(0) );
        p2.setText("" + res.getInt(1) );
        res.moveToLast();
        a3.setText("" + res.getString(0) );
        p3.setText("" + res.getInt(1) );

    }

    public void meinProfil(final View view) {
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }


    public void kaufNetflix(final View view){
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        newString = "'"+newString+"'";
        Cursor res = helper.sucheKaufe(newString);
        StringBuffer buffer = new StringBuffer();
        if(res.moveToFirst()) {
            buffer.append(res.getString(3));
            String Artikel = buffer.toString();
            if(Artikel.contains("Netflix")){
                artikelvorhanden();
            }else{
                Cursor guthabenc = helper.sucheGuthaben(newString);

                guthabenc.moveToFirst();
                int guthaben = guthabenc.getInt(0);
                TextView stv = (TextView) findViewById(R.id.netflix_preis);
                int netflixp = Integer.parseInt(stv.getText().toString());
                Log.i("kaufNetflix", ""+guthaben );
                Log.i("kaufNetflix", ""+netflixp );
                if (guthaben >= netflixp) {
                    handle_rest(guthaben, netflixp);
                }else{
                    planb();
                }
            }
        }
        }


    public void kaufSpotify(final View view){

        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        newString = "'"+newString+"'";
        Cursor res = helper.sucheKaufe(newString);
        StringBuffer buffer = new StringBuffer();
        if(res.moveToFirst()) {
            buffer.append(res.getString(3));
            String Artikel = buffer.toString();
            Log.i("kauf", Artikel);
            if(Artikel.contains("Spotify")){
                artikelvorhanden();
            }else{
                Cursor guthabenc = helper.sucheGuthaben(newString);

                guthabenc.moveToFirst();
                int guthaben = guthabenc.getInt(0);

                TextView stv = (TextView) findViewById(R.id.spotify_preis);
                int spotifyp = Integer.parseInt(stv.getText().toString());
                Log.i("kaufmich", "im zweiten knopf");
                if (guthaben >= spotifyp) {
                    handle_rest(guthaben, spotifyp);
                }else{
                    planb();
                }
            }
        }
}

    public void kaufMcFit(final View view){

        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        newString = "'"+newString+"'";
        Cursor res = helper.sucheKaufe(newString);
        StringBuffer buffer = new StringBuffer();
        if(res.moveToFirst()) {
            buffer.append(res.getString(3));
            String Artikel = buffer.toString();
            if(Artikel.contains("McFit")){
                artikelvorhanden();
            }else{
                Cursor guthabenc = helper.sucheGuthaben(newString);

                guthabenc.moveToFirst();
                int guthaben = guthabenc.getInt(0);

                TextView stv = (TextView) findViewById(R.id.mcfit_preis);
                int mcfitp = Integer.parseInt(stv.getText().toString());
                Log.i("kaufmich", "im zweiten knopf");
                if (guthaben >= mcfitp) {
                    handle_rest(guthaben, mcfitp);
                }else{
                    planb();
                }
            }
        }

    }

    public void artikelvorhanden(){
        String[] answers = {"Zurück zu den Angeboten"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Du hast den Artikel schon gekauft");
        builder.setItems(answers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });
        builder.show();
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
        String kauf ="";

        TextView ntv = (TextView) findViewById(R.id.netflix_preis);
        int netflixp = Integer.parseInt(ntv.getText().toString());

        TextView stv = (TextView) findViewById(R.id.spotify_preis);
        int spotifyp = Integer.parseInt(stv.getText().toString());

        if(preis == netflixp){
            kauf = "'Netflix'";
        }else if(preis == spotifyp){
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
            newString = "'"+newString+"'";
            Cursor guthabenc = helper.sucheGuthaben(newString);

            guthabenc.moveToFirst();
            int guthaben = guthabenc.getInt(0);
            tv_name.setText("Die Angebote gibt es für dich, " + newString + " und das ist dein Guthaben: " + guthaben + " Euro");
        }catch(Exception e){
            Log.d("Fehler", "Kein Guthaben in DB");
        }
        return newString;
    }


    public void viewAlll(final View view){
        Button btnviewall = (Button) findViewById(R.id.button2);
        btnviewall.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Cursor res = helper.getAllData();
                        Log.i("view all", "Kriegt den Cursor");
                        if(res.getCount()==0){
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Name: " + res.getString(0) );
                            buffer.append("Preis: " + res.getInt(1) );
                        }
                        //Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
