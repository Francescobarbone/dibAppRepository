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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrazioneActivity extends AppCompatActivity {

    private EditText emailId, password;
    private Button btnRegistrati;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore mFirestore;

    @Override
    public void onBackPressed(){
        Intent regToMain = new Intent(RegistrazioneActivity.this, MainActivity.class);
        startActivity(regToMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        mFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        btnRegistrati = findViewById(R.id.buttonRegister);
        btnRegistrati.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                final Map<String, String> userMap = new HashMap<>();
                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText( RegistrazioneActivity.this, R.string.campi_vuoti, Toast.LENGTH_SHORT).show();
                }
                else if(pwd.isEmpty()){
                    password.setError("Inserire la password");
                    password.requestFocus();
                }else if(email.isEmpty()){
                    emailId.setError("Inserire l'indirizzo email");
                    emailId.requestFocus();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    if(email.endsWith("@studenti.uniba.it")) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(Task<AuthResult> task){
                                if(task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegistrazioneActivity.this, R.string.registrazione_successo, Toast.LENGTH_LONG).show();
                                                emailId.setText("");
                                                password.setText("");
                                            } else {
                                                Toast.makeText(RegistrazioneActivity.this, R.string.registrazione_gia_effettuata, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                }
                    else
                     Toast.makeText(RegistrazioneActivity.this, R.string.reg_mail ,Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(RegistrazioneActivity.this, R.string.campi_vuoti,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
