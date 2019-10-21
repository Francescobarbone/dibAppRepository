package com.dibapp.dibapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class CommentCreateActivity extends AppCompatActivity {

    private EditText motivation;
    private FirebaseFirestore mFirestore;
    private Button saveComment;
    private RatingBar rates;
    private CheckBox anonymousComment;
    private boolean visibility = false;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(CommentCreateActivity.this, StudentActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        rates = (RatingBar)findViewById(R.id.ratingBar);
        saveComment = (Button) findViewById(R.id.buttonComment);
        motivation = (EditText)findViewById(R.id.editText);
        anonymousComment = (CheckBox)findViewById(R.id.checkBox);
        mFirestore = FirebaseFirestore.getInstance();

        final String corso = getIntent().getStringExtra("courseid");
        final String userEmail = getIntent().getStringExtra("user");
        final String less = getIntent().getStringExtra("lessonid");

        anonymousComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibility = true;
            }
        });

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((rates.getRating() == 0.0) && (motivation.getText().toString().isEmpty())) {
                    Toast.makeText(CommentCreateActivity.this, R.string.campi_vuoti, Toast.LENGTH_SHORT).show();
                } else if (rates.getRating() == 0.0) {
                    Toast.makeText(CommentCreateActivity.this, R.string.exp_valutazione, Toast.LENGTH_SHORT).show();
                } else if (motivation.getText().toString().isEmpty()) {
                    motivation.setError("Per favore, inserisca una motivation");
                    motivation.requestFocus();
                } else if (!((rates.getRating() == 0.0) && (motivation.getText().toString().isEmpty()))) {
                    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
                    String mComment = motivation.getText().toString();
                    Comment comment = new Comment();

                    //controllo sulla visibilità del commento
                    if(visibility)
                        comment = new Comment(mComment, currentDate, less, "Anonimo");
                    else
                        comment = new Comment(mComment, currentDate, less,userEmail);

                    mFirestore.collection("Courses /" + corso + "/Lessons/" + less + "/Comments").add(comment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CommentCreateActivity.this, R.string.grazie_voto, Toast.LENGTH_SHORT).show();
                                motivation.setText("");
                                startActivity(new Intent(CommentCreateActivity.this, StudentActivity.class));
                            }
                        }
                    });
                }
            }
        });


    }

}
