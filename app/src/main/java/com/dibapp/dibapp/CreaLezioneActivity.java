package com.dibapp.dibapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreaLezioneActivity extends AppCompatActivity {

    private EditText date, descrizione, oraInizio, oraFine;
    private Spinner spinnerCdL;
    private Button createLessons;

    private DatabaseReference databaseLessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_lezione);

        databaseLessons = FirebaseDatabase.getInstance().getReference("Lessons");

        descrizione = findViewById(R.id.argomento);
        date = findViewById(R.id.Date);
        oraInizio =findViewById(R.id.oraStart);
        oraFine =findViewById(R.id.oraEnd);
        spinnerCdL = findViewById(R.id.cDL);
        createLessons = findViewById(R.id.salvaLezione);


        createLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLessons();//metodo per la creazione della lezione
            }
        });
    }

    private void addLessons(){
        String argomento = descrizione.getText().toString();
        String data = date.getText().toString();
        String oraStart = oraInizio.getText().toString();
        String oraEnd = oraFine.getText().toString();
        String cDl = spinnerCdL.getSelectedItem().toString();

        if(!(TextUtils.isEmpty(argomento)) && !(TextUtils.isEmpty(data)) && !(TextUtils.isEmpty(oraStart)) &&
                !(TextUtils.isEmpty(oraEnd))){
            //la lezione viene creata solo nel caso in cui tutti i campi siano completi

            Lezione lessons = new Lezione(cDl, argomento, data, oraStart, oraEnd);

            databaseLessons.child(lessons.getID().toString()).setValue(lessons);

            Toast.makeText(CreaLezioneActivity.this, "Lezione creata", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(CreaLezioneActivity.this,"Completa tutti i campi", Toast.LENGTH_SHORT).show();
        }

    }
}
