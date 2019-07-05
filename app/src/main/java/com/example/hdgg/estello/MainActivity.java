package com.example.hdgg.estello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/*
@author Estelle Weinstock, Hai Do Le
@version 1.0
@since 2019-07-02
 */

public class MainActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /*

    Funktion speichert den eingegeben Text aus der Anmeldung, ruft die "erstellePasswort"
    Methode mit dem Parameter "pass", welches das soeben gespeicherte Passwort ist.
    Danach wird die Methode "suchePasswort" aufgerufen, welches entweder den String "Nicht auffindbar"
    zurück-gibt, wenn der Benutzer noch nicht existiert oder das Passwort, welches für den Benutzer
    gespeichert ist.
    Wenn das Passwort mit dem soeben eingegeben Passwort übereinstimmt, wird der Nutzer mit einem
    Intent in die Profil Klasse weitergeleitet und der Name vom Benutzer wird übergeben.
    Ansonsten wird ein Toast ausgegeben, dass die Anmeldedaten nicht korrekt sind.
    @param view
    @throws InvalidKeySpecException
    @throws NoSuchAlgorithmException

    */


    public void darfstDurch(final View view) throws InvalidKeySpecException, NoSuchAlgorithmException {

        EditText name = (EditText) findViewById(R.id.name_text);
        String str_name = name.getText().toString();

        EditText pass = (EditText) findViewById(R.id.pass_text);

        String str_pw = helper.erstellePasswort(pass.getText().toString());
        String passwortGeprueft = helper.suchePasswort(str_name);

        if (str_pw.equals(passwortGeprueft)) {
            Intent i = new Intent(MainActivity.this, Profil.class);
            i.putExtra("user_name", str_name);
            startActivity(i);
        }
        else {
            Toast temp = Toast.makeText(MainActivity.this, "Die Login Daten stimmen nicht",
                    Toast.LENGTH_SHORT);
            temp.show();
        }

    }

    /*

    Funktion leitet auf Knopfdruck auf die "ErstelleAccount" Seite
    @param view

     */

    public void machDirEinenAccount(final View view){
        Intent i = new Intent(MainActivity.this, ErstelleAccount.class);
        startActivity(i);
    }

}
