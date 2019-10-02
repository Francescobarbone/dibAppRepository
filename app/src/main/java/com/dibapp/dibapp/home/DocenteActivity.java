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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final User admin = new User();
        admin.setEmail(user.getEmail());

        textViewBenvenuto = (TextView) findViewById(R.id.textView3);

        if(user!=null)
            textViewBenvenuto.setText("Benvenuto " + user.getEmail().substring( 0, (user.getEmail().indexOf('@'))));


        showLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            String email = doc.getString("email");
                            if(email.equals(admin.getEmail())){
                                admin.setCourseId(doc.getString("idCorso"));
                                break;
                            }
                        }
                        Intent intent = new Intent(DocenteActivity.this, LezioniDocenteActivity.class);
                        intent.putExtra("idC",admin.getCourseId());
                        DocenteActivity.this.startActivity(intent);
                    }
                });
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
