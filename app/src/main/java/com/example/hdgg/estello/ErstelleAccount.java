package com.example.hdgg.estello;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ErstelleAccount extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erstelle_account);
    }


    //Datenbankhelfer Objekt erstellen
    DatabaseHelper helper = new DatabaseHelper(this);

    /**
     * Diese Methode verbindet die Activity mit der dazgehoerigen Layout Datei activtiy_erstelle_account
     * @param savedInstanceState
     */


    /**
     * Erstellung von einem neuen Account
     * @see <code> Kontakt</code>
     * @see <code> fuegKontaktEin()</code>
     * @param view
     */
    public void machRichtigenAccount(final View view){
        EditText userName = (EditText) findViewById(R.id.benutzername_neu);
        EditText userPasswort = (EditText) findViewById(R.id.benutzerpasswort_neu);

        String userNameNeu = userName.getText().toString();
        String userPasswortNeu= userPasswort.getText().toString();

        //Objekt Kontakt erstellen
        Kontakt k = new Kontakt();
        k.setUsername(userNameNeu);
        k.setUpasswort(userPasswortNeu);

        //Methode soll eingefügt werden
        helper.fuegKontaktEin( k );

        //Intent zur Kategorien-Activity
        Intent i =new Intent(ErstelleAccount.this, Kategorie.class);
        startActivity(i);
    }


}
