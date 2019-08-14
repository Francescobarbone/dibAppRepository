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

                if(email.isEmpty()){
                    emailId.setError("Inserisci l'email");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Inserisci una password");
                    password.requestFocus();
                }else if(email.isEmpty() && email.isEmpty()){
                    Toast.makeText( RegistrazioneActivity.this, "I campi sono vuoti!", Toast.LENGTH_SHORT);
                }
                else if(!(email.isEmpty() && email.isEmpty())){

                    if(email.endsWith("@studenti.uniba.it") || email.endsWith("@uniba.it")) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegistrazioneActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(RegistrazioneActivity.this,"Registrazione non Riuscita, Riprova!",Toast.LENGTH_SHORT);
                                }
                                else{
                                    startActivity(new Intent(RegistrazioneActivity.this, HomeActivity.class));
                                }
                            }
                        });

                        }
                    else {
                        Toast.makeText(RegistrazioneActivity.this, "Puoi eseguire la registrazione con un e-mail istituzionale",Toast.LENGTH_SHORT);
                    }

                }else{
                    Toast.makeText(RegistrazioneActivity.this, "Errore!", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
