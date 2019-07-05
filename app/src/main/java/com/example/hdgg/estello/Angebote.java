package com.example.hdgg.estello;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Angebote extends AppCompatActivity{

    DatabaseHelper helper = new DatabaseHelper(this);
    private List<Artikel> artikel_list;


    /*
     * Methode öffnet die Seite und ruft die Methode "getAlleArtikel" auf und speichert die
     * Artikel in "artikel_list". Wenn die Artikelliste leer ist, wird mit der Methode "artikelErstellen"
     * die Artikel erstellt und dann wird die Artikelliste mit "getAlleArtikel" beladen.
     *
     * @param savedInstanceState
     */


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

    }

    public void meinProfil(final View view) {
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }

    /*
     * Methode erstellt ein Bundle extras, um den Intent zu kriegen und deren Extras zu bekommen.
     * Ein neuer String wird erstellt, der über einen Schlüssel auf die Extras zugreifen kann.
     * Der String wird für Wiederverwendung zurückgegeben.
     *
     * @return newString
     */

    public String welcherName() {
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        return newString;
    }

    /*
     * Methode ruft den Namen des eingeloggten Benutzers auf und ruft damit die Methode "sucheKaufe"
     * auf. Diese werden in einem Cursor res gespeichert und es wird ein StringBuffer erstellt.
     * Dieser Buffer wird mit dem String aus der Käufe Spalte befüllt und dann wird verglichen, ob der
     * Artikel schon vorhanden ist. Wenn dem so ist, wird die Methode "artikelvorhanden" aufgerufen.
     * Ansonsten wird die Methode "sucheGuthaben" aufgerufen, welche prüft, ob das Guthaben des Benutzers
     * ausreichend ist. Wenn das Guthaben >= Preis vom Artikel ist, wird die Methode "handle_rest" auf-
     * gerufen. Ansonsten wird "planb" aufgerufen.
     *
     * @pararm view
     */


    public void kaufNetflix(final View view){

        String newString = welcherName();
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


        String newString = welcherName();
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

        String newString = welcherName();
        newString = "'"+newString+"'";
        Cursor res = helper.sucheKaufe(newString);
        StringBuffer buffer = new StringBuffer();
        if(res.moveToFirst()) {
            buffer.append(res.getString(3));
            String Artikel = buffer.toString();
            if(Artikel.contains("Mcfit")){
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


    /*
     * Diese Methode wird aufgerufen, wenn der User den Artikel bereits gekauft hat. Der Benutzer
     * hat nun die Möglichkeit zurück zu den Angeboten zu gehen und der Benutzer bekommt die Meldung,
     * dass er den Artikel schon erstanden hat. Dies wird mithilfe eines AlertDialogs verwirklicht.
     *
     */

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

    /*
     * Diese Methode wird aufgerufen, wenn der User nicht genug Geld hat den Artikel zu kaufen.
     * Der Benutzer hat nun die Möglichkeit zurück zu den Angeboten zu gehen und der Benutzer bekommt
     * die Meldung,dass er nicht genug Geld hat. Dies wird mithilfe eines AlertDialogs
     * verwirklicht.
     *
     */

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

    /*
     * Diese Methode wird aufgerufen, wenn der Benutzer den Artikel noch nicht gekauft hat und
     * noch
     *
     *
     * @param guthaben
     * @param preis
     */
    public void handle_rest(final int guthaben, final int preis) {
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

    /*
     * Methode holt sich die Namen mithilfe von den Intent Extras und ruft dann die Methode
     * "sucheGuthaben" auf. Das Ergebnis wird in einem Cursor gespeichert und danach wird
     * das erste (und einzige) Ergebnis als int herausgefiltert und dann wird eine TextView
     * ausgegeben mit dem Namen und dem Guthaben.
     * Ansonsten wird ein Fehler ausgegeben, dass kein Guthaben vorhanden ist.
     *
     * @return newString
     */

    public String greetMe(){

        String newString = welcherName();
        TextView tv_name = (TextView) findViewById(R.id.textView);
        try {
            newString = "'"+newString+"'";
            Cursor guthabenc = helper.sucheGuthaben(newString);

            guthabenc.moveToFirst();
            int guthaben = guthabenc.getInt(0);
            tv_name.setText("Die Angebote gibt es für dich, " + newString +
                    " und das ist dein Guthaben: " + guthaben + " Euro");
        }catch(Exception e){
            Log.d("Fehler", "Kein Guthaben in DB");
        }
        return newString;
    }

}
