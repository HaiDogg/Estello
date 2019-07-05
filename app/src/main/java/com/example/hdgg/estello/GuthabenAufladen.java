package com.example.hdgg.estello;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }

    /**
     * Methode bekommt den Benutzernamen aus dem Intent Extras und erstellt eine Integer Variable
     * "int_neuesguthaben". Die Methode versucht aus dem EditText den Test zu lesen, diesen in einen
     * String und in einen Int umzuwandeln. Wenn das nicht geht, wird der Benutzer gebeten eine Ganzzahl
     * einzugeben mit einem Toast.
     * Wenn der Integer in der Variable "int_neuesguthaben" gespeichert ist, wird die Methode
     * "sucheGuthaben" mit dem Namen des Benutzers aufgerufen. Das Ergebnis wird in einem Cursor
     * zurückgegeben und der Eintrag in der ersten Spalte wird zurückgegeben.
     * Nun wird das alte Guthaben mit dem neuen addiert und in der Variable "neues_guthaben" gespeichert.
     * Die Methode "ladDbAuf" wirf aufgerufen und das Guthaben wird überschrieben.
     * Das neue Guthaben wird angezeigt.
     *
     * @param view
     */


    public void ladAuf(final View view){
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("user_name");

        int int_neuesguthaben;

        try {
            EditText tv = (EditText) findViewById(R.id.editText_aufladen) ;
            String str_neuesguthaben = tv.getText().toString();
            int_neuesguthaben = Integer.parseInt(str_neuesguthaben);
        }
        catch(NumberFormatException e)
        {
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
        helper.updateGuthabenDB(neues_guthaben, name);

        TextView tv_neuesgutgaben = (TextView) findViewById(R.id.textView6);
        tv_neuesgutgaben.setText(""+neues_guthaben);
    }

}
