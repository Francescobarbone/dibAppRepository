package com.dibapp.dibapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dibapp.dibapp.R;
import com.google.firebase.auth.FirebaseAuth;
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

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((valutazione.getRating() == 0.0)&& (motivazione.getText().toString().isEmpty())){
                    Toast.makeText(CreaCommentoActivity.this, "I Campi sono vuoti", Toast.LENGTH_SHORT).show();
                }else if(valutazione.getRating() == 0.0){
                    Toast.makeText(CreaCommentoActivity.this, "Valutazione non espressa", Toast.LENGTH_SHORT).show();
                }else if(motivazione.getText().toString().isEmpty()){
                    Toast.makeText(CreaCommentoActivity.this, "Motivazione non espressa", Toast.LENGTH_SHORT).show();
                }else if(!((valutazione.getRating() == 0.0)&& (motivazione.getText().toString().isEmpty()))){
                    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
                    String mComment = motivazione.getText().toString();
                    //Comment commento = new Comment(mComment, currentDate, );
                    //mFirestore.collection("Courses /"+ +"/Lessons/"++"/Comments").add(Comment)
                }
            }
        });


    }
}
