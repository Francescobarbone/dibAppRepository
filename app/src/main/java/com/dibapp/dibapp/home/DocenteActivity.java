package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class DocenteActivity extends HomeActivity {

    private Button showLess, createless, logOut;
    private TextView textViewBenvenuto;
    private FirebaseAuth firebaseAuth;
    private final User admin = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        showLess = findViewById(R.id.showLessons);
        createless = findViewById(R.id.creaLezione);
        logOut = findViewById(R.id.logoutDocente);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewBenvenuto = (TextView) findViewById(R.id.textView3);

        if(user!=null)
            textViewBenvenuto.setText("             Benvenuto " + user.getEmail().substring( 0, (user.getEmail().indexOf('@'))));

        mFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean find = false;
                for (DocumentSnapshot doc : task.getResult()) {
                    String email = doc.getString("email");
                    if (email.equals(admin.getEmail())) {
                        find = true;
                        //getting admin's courseID
                        admin.setCourseId(doc.getString("courseId"));
                        break;
                    }
                }
            }
        });

        showLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DocenteActivity.this, LessonActivity.class));
            }
        });

        createless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DocenteActivity.this, CreaLezioneActivity.class));
            }
        });


        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                firebaseAuth.signOut();
                Intent intToMain = new Intent(DocenteActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });
    }
}
