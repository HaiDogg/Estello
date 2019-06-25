package com.example.hdgg.estello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ErstelleAccount extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erstelle_account);
    }


    //Datenbankhelfer Objekt erstellen
    DatabaseHelper helper = new DatabaseHelper(this);

    /**
     * Diese Methode verbindet die Activity mit der dazgehoerigen Layout Datei activtiy_erstelle_account
     * @param savedInstanceState
     */


    /**
     * Erstellung von einem neuen Account
     * @see <code> Kontakt</code>
     * @see <code> fuegKontaktEin()</code>
     * @param view
     */
    public void machRichtigenAccount(final View view) throws InvalidKeySpecException, NoSuchAlgorithmException {
        EditText userName = (EditText) findViewById(R.id.benutzername_neu);
        EditText userPasswort = (EditText) findViewById(R.id.benutzerpasswort_neu);

        String str_name = userName.getText().toString();
        String userPasswortNeu= userPasswort.getText().toString();

        //Objekt Kontakt erstellen
        Benutzer b = new Benutzer();
        b.setBenutzer_name(str_name);
        b.setBenutzer_pw(helper.erstellePasswort(userPasswortNeu));

        //Methode soll eingefügt werden
        helper.fuegKontaktEin( b );

        //Intent zur Kategorien-Activity
        Intent i =new Intent(ErstelleAccount.this, Profil.class);
        i.putExtra("user_name", str_name);
        startActivity(i);
    }



}
