package com.example.hdgg.estello;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GuthabenAufladen extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guthaben_aufladen);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    public void ladAuf(final View view){
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("user_name");

        int int_neuesguthaben;
        try {
            EditText tv = (EditText) findViewById(R.id.editText_aufladen) ;
            String str_neuesguthaben = tv.getText().toString();
            int_neuesguthaben = Integer.parseInt(str_neuesguthaben);
        }catch(NumberFormatException e){
            int_neuesguthaben =0;
            Context context = getApplicationContext();
            CharSequence text = "Bitte gib eine Ganzzahl ein.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        name = "'" + name + "'";
        Cursor guthabenc = helper.sucheGuthaben(name);

        guthabenc.moveToFirst();
        int guthaben = guthabenc.getInt(0);
        int neues_guthaben = guthaben + int_neuesguthaben;
        helper.laddbauf(name, neues_guthaben);

        TextView tv_neuesgutgaben = (TextView) findViewById(R.id.textView6);
        tv_neuesgutgaben.setText(""+neues_guthaben);
    }

}
