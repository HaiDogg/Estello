package com.example.hdgg.estello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ErstelleAccount extends AppCompatActivity {

    /**
     * Diese Methode verbindet die Activity mit der dazgehoerigen Layout Datei activtiy_erstelle_account
     * @param savedInstanceState
     */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erstelle_account);
    }

    //Datenbankhelfer Objekt erstellen
    DatabaseHelper helper = new DatabaseHelper(this);


    /**
     * Funktion speichert Werte aus den Eingabefeldern, ein neues Benutzerobjekt wird erstellt.
     * Es wird ein neues Passwort erstellt, indem die "erstellePasswort" Funktion aufgerufen wird.
     * Nun werden die Benutzerattribute auf den Namen und das neue Passwort gesetzt und der Kontakt
     * mit der "fuegKontaktEin" Methode in die Datenbank eingespeist.
     * Danach wird der Benutzer auf die Profil Seite weitergeleitet.
     * @see <code> Kontakt</code>
     * @see <code> fuegKontaktEin()</code>
     * @param view
     */


     public void machRichtigenAccount(final View view) throws InvalidKeySpecException, NoSuchAlgorithmException {
        EditText userName = (EditText) findViewById(R.id.benutzername_neu);
        EditText userPasswort = (EditText) findViewById(R.id.benutzerpasswort);

        String str_name = userName.getText().toString();
        String userPasswortNeu= userPasswort.getText().toString();

        Benutzer b = new Benutzer();
        userPasswortNeu =helper.erstellePasswort(userPasswortNeu);

        b.setBenutzer_name(str_name);
        b.setBenutzer_pw(userPasswortNeu);

        helper.fuegKontaktEin( b );

        Intent i =new Intent(ErstelleAccount.this, Profil.class);
        i.putExtra("user_name", str_name);
        startActivity(i);
    }



}
