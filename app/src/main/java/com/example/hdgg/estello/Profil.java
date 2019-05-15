package com.example.hdgg.estello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Profil extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        welcherName();
    }

    public void gehZuAngebote(final View view){

        String newString = welcherName();
        Intent i = new Intent(Profil.this, Angebote.class);
        i.putExtra("brauche", newString);
        startActivity(i);
    }
    public String welcherName() {
        String newString;
        Bundle extras = getIntent().getExtras();
        newString = extras.getString("brauche_ich");
        zeigNamean(newString);
        return newString;
    }


    public void zeigKaufean(String kauf){
        String mein_kauf= getIntent().getStringExtra("kauf");
        TextView kaufe = (TextView) findViewById(R.id.kaufe_id);
        kaufe.setText(mein_kauf);
    }

    public void zeigNamean(String newString){

        TextView tv_name = (TextView) findViewById(R.id.profil_name_id);
        tv_name.setText("Hallo, "+ newString );

    }

    public void updateListe(String name_von_textview){
        TextView alt = (TextView) findViewById(R.id.kaufe_id);
        CharSequence old = alt.getText();
        TextView textView = (TextView) findViewById(R.id.kaufe_id);
        textView.setText(old + name_von_textview);
    }
}