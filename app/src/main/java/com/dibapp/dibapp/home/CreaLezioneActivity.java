package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreaLezioneActivity extends AppCompatActivity {

    private EditText argomento;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth firebaseAuth;
    private Button saveLesson;

    //admin per memorizzare le info
    private final User admin = new User();

    @Override
    public void onBackPressed(){
        startActivity(new Intent(CreaLezioneActivity.this, DocenteActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_lezione);

        //Istanza firebase dell'utente in uso corrente
        firebaseAuth = FirebaseAuth.getInstance();
        argomento = findViewById(R.id.argText);
        saveLesson = findViewById(R.id.saveLessonButton);

        //Memorizza utente in uso corrente
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //salvo le info dell'user
        admin.setEmail(user.getEmail());

        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean find = false;
                for (DocumentSnapshot doc : task.getResult()) {
                    String email = doc.getString("email");
                    if (email.equals(admin.getEmail())) {
                        find = true;
                        //getting admin's courseID
                        admin.setCourseId(doc.getString("idCorso"));
                        break;
                    }
                }
                if(find)
                    saveLesson.setVisibility(View.VISIBLE);
            }
        });

        //Metodo per la creazione della lezione
        saveLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
                String arg = argomento.getText().toString();

                if(arg.isEmpty()){
                    argomento.setError("Inserisci un argomento");
                    argomento.requestFocus();
                } else {
                    final Lesson less = new Lesson(admin.getCourseId(), currentDate, arg);
                        mFirestore.collection("Courses /" + admin.getCourseId() + "/Lessons").add(less).addOnCompleteListener(CreaLezioneActivity.this, new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreaLezioneActivity.this, R.string.lezione_inserita, Toast.LENGTH_SHORT).show();
                                    argomento.setText("");
                                    startActivity(new Intent(CreaLezioneActivity.this, DocenteActivity.class));
                                }
                            }
                        });
                }
            }
        });
    }
}
