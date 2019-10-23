package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

//Activity per la gestione dell'interfaccia studente
public class StudentActivity extends HomeActivity {

    private Button viewLessons;
    private TextView textViewWelcome;
    private FirebaseAuth firebaseAuth;

    //Creazione dell'overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if(item.getItemId() == R.id.home){
                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        viewLessons = findViewById(R.id.butLezione);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewWelcome = findViewById(R.id.benvenutoId);
        textViewWelcome.setText(Objects.requireNonNull(Objects.requireNonNull(user).getEmail()).substring(0, (Objects.requireNonNull(user.getEmail()).indexOf('@'))));


        //Visualizzazione dei corsi, lezione e commenti
        viewLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studToQR = new Intent(StudentActivity.this, CourseActivity.class);
                startActivity(studToQR);
            }
        });

    }
}
