package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dibapp.dibapp.autenticazione.MainActivity;
import com.dibapp.dibapp.QRCodeScanner;
import com.dibapp.dibapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudenteActivity extends HomeActivity {

    private Button logOut;
    private Button visualizzaLezione;

    private TextView textViewBenvenuto;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studente);

        visualizzaLezione = findViewById(R.id.butLezione);
        logOut = findViewById(R.id.logoutStudente);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewBenvenuto = (TextView) findViewById(R.id.textView4);
        textViewBenvenuto.setText("Benvenuto " + user.getEmail().substring( 0, (user.getEmail().indexOf('@'))));

        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(StudenteActivity.this, MainActivity.class);
                startActivity(intToMain);
                finishAffinity();
            }
        });

        visualizzaLezione.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent studToQR = new Intent(StudenteActivity.this, LezioneActivity.class);
                startActivity(studToQR);
            }
        });
    }
}
