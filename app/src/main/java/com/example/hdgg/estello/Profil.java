package com.example.hdgg.estello;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Profil extends AppCompatActivity {

    Button btnaufladen;
    Button btnviewartikel;
    DatabaseHelper helper = new DatabaseHelper(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        btnaufladen = (Button) findViewById(R.id.lad_mich_auf_id);
        btnviewartikel = (Button) findViewById(R.id.kauf_anzeigen);
        welcherName();
        showGuthaben();
    }


    public String welcherName() {
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        TextView tv_name = (TextView) findViewById(R.id.frage_aufladen);
        tv_name.setText("Hallo, "+ newString );
        return newString;
    }
/*
    public void viewAlll(final View view){
        btnviewall.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Cursor res = helper.getAllData();
                        Log.i("view all", "Kriegt den Cursor");
                        if(res.getCount()==0){
                            // show message
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                           buffer.append("Name: " + res.getString(0) );
                           buffer.append("Passwort: " + res.getString(1) );
                           buffer.append("Guthaben: " + res.getString(2) );
                           buffer.append(helper.COLUMN_ARTIKEL + res.getString(3) );
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
*/

    public void zeigKaufean(final View view){
        btnviewartikel.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        //ruf funk in helper auf
                        Bundle extras = getIntent().getExtras();
                        String name = extras.getString("user_name");
                        name = "'"+name+"'";

                            Cursor res = helper.sucheKaufe(name);

                            StringBuffer buffer = new StringBuffer();
                            if(res.moveToFirst()) {
                            buffer.append(res.getString(3));
                            String Artikel = buffer.toString();
                            TextView tv = (TextView) findViewById(R.id.kaufe_id);
                            tv.setText(Artikel);
                        }
                    }
                }
        );
    }

    public void gehZuAngebote(final View view){

        String newString = welcherName();
        Intent i = new Intent(Profil.this, Angebote.class);
        i.putExtra("user_name", newString);
        startActivity(i);
    }

    public void showGuthaben(){
        Bundle extras = getIntent().getExtras();
        String newString = extras.getString("user_name");
        TextView tv_guthaben = (TextView) findViewById(R.id.guthaben_id);
        try {
            int guthaben = helper.sucheGuthaben(newString);
            tv_guthaben.setText(guthaben + " Euro");
        }catch(Exception e){
            Log.d("Fehler", "Kein Guthaben in DB");
        }
    }

    public void aufladen(final View view){
        String newString = welcherName();
        Intent i = new Intent(Profil.this, GuthabenAufladen.class);
        i.putExtra("user_name", newString);
        startActivity(i);
    }
}