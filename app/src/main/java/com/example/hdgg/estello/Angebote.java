package com.example.hdgg.estello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Angebote extends AppCompatActivity {

    // hier kommen die Kategorien rein

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorien);
    }

    public void meinProfil(final View view){
        Intent i = new Intent(Angebote.this, Profil.class);
        startActivity(i);
    }
}
