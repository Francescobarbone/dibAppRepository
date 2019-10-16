package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dibapp.dibapp.QRCodeScanner;
import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentActivity extends HomeActivity {

    private Button logOut;
    private Button visualizzaLezione;
    private Button seguiLezione;
    private TextView textViewBenvenuto;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studente);
        seguiLezione = findViewById(R.id.seguiLezione);
        visualizzaLezione = findViewById(R.id.butLezione);
        logOut = findViewById(R.id.logoutStudente);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewBenvenuto = (TextView) findViewById(R.id.benvenutoId);
        textViewBenvenuto.setText(user.getEmail().substring(0, (user.getEmail().indexOf('@'))));


        //Logout
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(StudentActivity.this, MainActivity.class);
                startActivity(intToMain);
                finishAffinity();
            }
        });

        //Accesso alla lezione previa scansione del codice QR
        seguiLezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentActivity.this, QRCodeScanner.class));
            }
        });

        //Visualizzazione dei corsi, lezione e commenti
        visualizzaLezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studToQR = new Intent(StudentActivity.this, CourseActivity.class);
                startActivity(studToQR);
            }
        });

        /*@Override
        public boolean onOptionsItemSelected (MenuItem item){

        }*/
    }
}
