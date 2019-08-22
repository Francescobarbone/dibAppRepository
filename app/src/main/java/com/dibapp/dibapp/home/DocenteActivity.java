package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dibapp.dibapp.CreaLezioneActivity;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.dibapp.dibapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DocenteActivity extends HomeActivity {

    private Button showLess, createless, logOut;
    private TextView textViewBenvenuto;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        showLess = findViewById(R.id.showLessons);
        createless = findViewById(R.id.creaLezione);
        logOut = findViewById(R.id.logoutDocente);


        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewBenvenuto = (TextView) findViewById(R.id.textView3);
        textViewBenvenuto.setText("Benvenuto " + user.getEmail().substring( 0, (user.getEmail().indexOf('@'))));

        createless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent docToCreate = new Intent(DocenteActivity.this, CreaLezioneActivity.class);
                startActivity(docToCreate);
            }
        });


        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                firebaseAuth.signOut();
                Intent intToMain = new Intent(DocenteActivity.this, MainActivity.class);
                startActivity(intToMain);
                finishAffinity();
            }
        });
    }
}
