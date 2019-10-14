package com.dibapp.dibapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class CreaCommentoActivity extends AppCompatActivity {

    private EditText motivazione;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth firebaseAuth;
    private Button saveComment;
    private RatingBar valutazione;


    @Override
    public void onBackPressed(){
        startActivity(new Intent(CreaCommentoActivity.this, StudenteActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_commento);

        valutazione = (RatingBar)findViewById(R.id.ratingBar);
        saveComment = (Button) findViewById(R.id.buttonComment);
        motivazione = (EditText)findViewById(R.id.editText);
        mFirestore = FirebaseFirestore.getInstance();

        final String corso = getIntent().getStringExtra("course_id");
        final String userEmail = getIntent().getStringExtra("email");
        final String less = getIntent().getStringExtra("lesson_id");

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((valutazione.getRating() == 0.0)&& (motivazione.getText().toString().isEmpty())){
                    Toast.makeText(CreaCommentoActivity.this, R.string.campi_vuoti, Toast.LENGTH_SHORT).show();
                }else if(valutazione.getRating() == 0.0){
                    Toast.makeText(CreaCommentoActivity.this, R.string.exp_valutazione, Toast.LENGTH_SHORT).show();
                }else if(motivazione.getText().toString().isEmpty()){
                    motivazione.setError("Per favore, inserisca una motivazione");
                    motivazione.requestFocus();
                }else if(!((valutazione.getRating() == 0.0)&& (motivazione.getText().toString().isEmpty()))){
                    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
                    String mComment = motivazione.getText().toString();
                    Comment commento = new Comment(mComment, currentDate, less);
                    mFirestore.collection("Courses /" + corso + "/Lessons/" + less + "/Comments").add(commento).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CreaCommentoActivity.this, "Commento creato", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
