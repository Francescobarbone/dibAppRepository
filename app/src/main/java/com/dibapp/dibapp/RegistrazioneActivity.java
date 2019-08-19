package com.dibapp.dibapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrazioneActivity extends AppCompatActivity {

    EditText emailId, password;
    Button btnRegistrati;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        btnRegistrati = findViewById(R.id.button2);
        btnRegistrati.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText( RegistrazioneActivity.this, "I campi sono vuoti!", Toast.LENGTH_SHORT).show();
                }
                else if(pwd.isEmpty()){
                    password.setError("Inserisci una password");
                    password.requestFocus();
                }else if(email.isEmpty()){
                    emailId.setError("Inserisci l'email");
                    emailId.requestFocus();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    //Aggiungi procedura di verifica e-mail
                    if(email.endsWith("@studenti.uniba.it") || email.endsWith("@uniba.it")) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(Task<AuthResult> task){
                                if(task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegistrazioneActivity.this, "Registrazione avvenuta con successo. Controlla la casella di posta per la verifica.", Toast.LENGTH_LONG).show();
                                                emailId.setText("");
                                                password.setText("");
                                                finishAffinity();
                                            } else {
                                                Toast.makeText(RegistrazioneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                emailId.setText("");
                                                password.setText("");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                } else
                     Toast.makeText(RegistrazioneActivity.this, "Puoi eseguire la registrazione  solo con un e-mail istituzionale",Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(RegistrazioneActivity.this, "I campi sono vuoti!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
