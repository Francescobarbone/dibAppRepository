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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class CommentCreateActivity extends AppCompatActivity {

    private EditText motivazione;
    private FirebaseFirestore mFirestore;
    private Button saveComment;
    private RatingBar valutazione;


    @Override
    public void onBackPressed(){
        startActivity(new Intent(CommentCreateActivity.this, StudentActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        valutazione = (RatingBar)findViewById(R.id.ratingBar);
        saveComment = (Button) findViewById(R.id.buttonComment);
        motivazione = (EditText)findViewById(R.id.editText);
        mFirestore = FirebaseFirestore.getInstance();

        final String corso = getIntent().getStringExtra("courseid");
        final String userEmail = getIntent().getStringExtra("user");
        final String less = getIntent().getStringExtra("lessonid");

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((valutazione.getRating() == 0.0)&& (motivazione.getText().toString().isEmpty())){
                    Toast.makeText(CommentCreateActivity.this, R.string.campi_vuoti, Toast.LENGTH_SHORT).show();
                }else if(valutazione.getRating() == 0.0){
                    Toast.makeText(CommentCreateActivity.this, R.string.exp_valutazione, Toast.LENGTH_SHORT).show();
                }else if(motivazione.getText().toString().isEmpty()){
                    motivazione.setError("Per favore, inserisca una motivazione");
                    motivazione.requestFocus();
                }else if(!((valutazione.getRating() == 0.0) && (motivazione.getText().toString().isEmpty()))){
                    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
                    String mComment = motivazione.getText().toString();
                    Comment commento = new Comment(mComment, currentDate, less, userEmail);
                    mFirestore.collection("Courses /" + corso + "/Lessons/" + less + "/Comments").add(commento).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CommentCreateActivity.this, R.string.grazie_voto, Toast.LENGTH_SHORT).show();
                                motivazione.setText("");
                                startActivity(new Intent(CommentCreateActivity.this, StudentActivity.class));
                            }
                        }
                    });
                }
            }
        });


    }
}
