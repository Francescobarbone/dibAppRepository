package com.dibapp.dibapp.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dibapp.dibapp.autenticazione.MainActivity;
import com.dibapp.dibapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private Button btnLogout;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //salva dati per preferenze condivise
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("Mia_lingua", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("Mia_lingua", "");
        setLocale(language);
    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(R.string.intent_logout);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
                finishAffinity();
            }
        });
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadLocale();
        btnLogout = findViewById(R.id.logout);

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
                finishAffinity();
            }
        });
    }

}
