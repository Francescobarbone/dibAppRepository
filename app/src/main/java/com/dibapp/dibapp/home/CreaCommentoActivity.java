package com.dibapp.dibapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.dibapp.dibapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreaCommentoActivity extends AppCompatActivity {

    private EditText motivazione;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth firebaseAuth;
    private Button saveComment;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(CreaCommentoActivity.this, StudenteActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_commento);

    }
}
