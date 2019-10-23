package com.dibapp.dibapp.home;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;

import com.dibapp.dibapp.autenticazione.MainActivity;
import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

//Activity per la gestione dell'interfaccia docente
public class TeacherActivity extends HomeActivity {

    private Button showLess, createless, logOut, QR;
    private TextView welcomeTeacher;
    private FirebaseAuth firebaseAuth;
    private static User admin = new User();


    //Creazione dell'overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Scelta dell'opzione più gradita
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if(item.getItemId() == R.id.home){
            startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        showLess = findViewById(R.id.showLessons);
        createless = findViewById(R.id.creaLezione);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        welcomeTeacher = findViewById(R.id.textView3);

        //Ottengo sottostringa dell'indirizzo email fino al simbolo @
        if(user!=null)
            welcomeTeacher.setText(Objects.requireNonNull(user.getEmail()).substring( 0, (user.getEmail().indexOf('@'))));

        //Mostra le lezioni relative al docente in uso corrente
        showLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, LessonActivity.class));
            }
        });

        //Crea le lezioni relative al corso insegnato dal docente in uso corrente
        createless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, LessonCreateActivity.class));
            }
        });
        
    }
}
