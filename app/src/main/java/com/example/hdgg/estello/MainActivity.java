package com.example.hdgg.estello;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    //public static final String LOG_TAG = MainActivity.class.getSimpleName();


    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*Funktion, die prüft, ob der Benutzer schon existiert und wenn dem so ist, das Passwort prüft.
    Wenn das Passwort stimmt, wird der Benutzer zu der "Angebote" Activity gefürt, ansonsten wird
    der Toast ausgegeben, dass die Login Daten nicht stimmen */
    public void darfstDurch(final View view){
        EditText name = (EditText) findViewById(R.id.name_text);
        EditText pass = (EditText) findViewById(R.id.pass_text);
        String str_name = name.getText().toString();
        String str_pw = pass.getText().toString();
        String passwortGeprueft = helper.suchePasswort(str_name);

        if (str_pw.equals(passwortGeprueft)) {
            Intent i = new Intent(MainActivity.this, Angebote.class);
            startActivity(i);
        }
        else {
            Toast temp = Toast.makeText(MainActivity.this, "Die Login Daten stimmen nicht",
                    Toast.LENGTH_SHORT);
            temp.show();
        }

    }

    //Funktion, welche zu Activity "Erstelle Account" führt
    public void machDirEinenAccount(final View view){
        Intent i = new Intent(MainActivity.this, ErstelleAccount.class);
        startActivity(i);
    }

}
