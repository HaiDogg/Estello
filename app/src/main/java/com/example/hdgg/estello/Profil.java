package com.example.hdgg.estello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Profil extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
    }

    public void gehZuAngebote(final View view){
        Intent i = new Intent(Profil.this, Angebote.class);
        startActivity(i);
    }

    public void updateListe(String name_von_textview){
        TextView alt = (TextView) findViewById(R.id.kaufe_id);
        CharSequence old = alt.getText();
        TextView textView = (TextView) findViewById(R.id.kaufe_id);
        textView.setText(old + name_von_textview);
    }
}

