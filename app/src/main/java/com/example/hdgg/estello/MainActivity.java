package com.example.hdgg.estello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // falls noch kein Account vorhanden ist, kann der User sich über diese Seite einen erstellen
    // wird zur nächsten Activity geleitet
    public void machDirEinenAccount(final View view){
        Intent i = new Intent(MainActivity.this, ErstelleAccount.class);
        startActivity(i);
    }
}
