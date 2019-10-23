package com.dibapp.dibapp.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText userEmail;
    private Button userPass;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onBackPressed(){
        Intent forToMain = new Intent(ForgotPasswordActivity.this, MainActivity.class);
        startActivity(forToMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        userEmail = findViewById(R.id.invioButtonForg);
        userPass = findViewById(R.id.buttonInvioFP);

        //Istanza di firebase relativa all'autenticazione
        firebaseAuth = FirebaseAuth.getInstance();

        //Bottone da cliccare per il ripristino password
        userPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!(userEmail.getText().toString().isEmpty())){
                    firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, R.string.verify, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    userEmail.setError(getString(R.string.inserisci_email));
                    userEmail.requestFocus();
                }
            }
        });
    }


}
