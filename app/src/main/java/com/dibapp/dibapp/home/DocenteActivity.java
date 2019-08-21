package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dibapp.dibapp.autenticazione.MainActivity;
import com.dibapp.dibapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DocenteActivity extends HomeActivity {

    private Button logOut;
    private TextView textViewBenvenuto;
    private FirebaseAuth firebaseAuth;
    private EditText argomento, corsoDiLaurea, data;
    //private Lezione lezione = new Lezione(corsoDiLaurea.getText().toString(), argomento.getText().toString(), data.getText().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        logOut = findViewById(R.id.logoutDocente);


        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewBenvenuto = (TextView) findViewById(R.id.textView3);
        textViewBenvenuto.setText("Benvenuto " + user.getEmail().substring( 0, (user.getEmail().indexOf('@'))));

        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(DocenteActivity.this, MainActivity.class);
                startActivity(intToMain);
                finishAffinity();
            }
        });
    }
}
