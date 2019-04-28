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
        zeigNamean(savedInstanceState);



    }
    public String zeigNamean(Bundle savedInstanceState){
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("brauche_ich");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("brauche_ich");
        }

        TextView tv_name = (TextView) findViewById(R.id.profil_name_id);
        tv_name.setText("Hallo, "+ newString );
        return newString;
    }

    public void gehZuAngebote(final View view, String newString){

        Intent i = new Intent(Profil.this, Angebote.class);
        i.putExtra("brauche", newString);
        startActivity(i);
    }

    public void updateListe(String name_von_textview){
        TextView alt = (TextView) findViewById(R.id.kaufe_id);
        CharSequence old = alt.getText();
        TextView textView = (TextView) findViewById(R.id.kaufe_id);
        textView.setText(old + name_von_textview);
    }
}

