package com.dibapp.dibapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailId, password;
    private Button btnSignIn;
    private TextView tvSignUp;
    private TextView forgotPsw;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        btnSignIn = findViewById(R.id.button2);
        tvSignUp = findViewById(R.id.textView);
        forgotPsw = findViewById(R.id.textView2);

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fireBaseUser = firebaseAuth.getCurrentUser();
                if (fireBaseUser != null && fireBaseUser.isEmailVerified()) {
                    if(emailId.getText().toString().endsWith("@uniba.it")){
                        Toast.makeText(MainActivity.this, "Login avvenuto con successo!", Toast.LENGTH_SHORT).show();
                        Intent intToHome = new Intent(MainActivity.this, DocenteActivity.class);
                        startActivity(intToHome);
                    }
                    if(emailId.getText().toString().endsWith("@studenti.uniba.it")){
                        Toast.makeText(MainActivity.this, "Login avvenuto con successo!", Toast.LENGTH_SHORT).show();
                        Intent intToHome = new Intent(MainActivity.this, StudenteActivity.class);
                        startActivity(intToHome);
                    }
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String email = emailId.getText().toString();
                String psw = password.getText().toString();
                if(email.isEmpty() && psw.isEmpty()){
                    Toast.makeText(MainActivity.this, "I campi sono vuoti!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    password.setError("Per favore, inserisca l'indirizzo e-mail");
                    password.requestFocus();
                } else if(psw.isEmpty()){
                    password.setError("Per favore, inserisca la password");
                    password.requestFocus();
                }
                else if (!(email.isEmpty() && psw.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(email, psw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Errore durante il login, riprova", Toast.LENGTH_SHORT).show();
                            } else {
                                if(email.endsWith("@studenti.uniba.it")){
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        Intent intToHome = new Intent(MainActivity.this, StudenteActivity.class);
                                        startActivity(intToHome);
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Verifica la tua E-mail", Toast.LENGTH_SHORT).show();
                                    }

                                }else if(email.endsWith("@uniba.it")){
                                    Intent intToHome = new Intent(MainActivity.this, DocenteActivity.class);
                                    startActivity(intToHome);
                                }
                            }
                        }
                    });
                } else
                    Toast.makeText(MainActivity.this, "Errore durante il login", Toast.LENGTH_SHORT).show();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intSignUp = new Intent (MainActivity.this, RegistrazioneActivity.class);
                startActivity(intSignUp);
            }
        });

        forgotPsw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intFrgPw = new Intent (MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intFrgPw);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}