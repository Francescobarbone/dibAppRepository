package com.dibapp.dibapp.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.home.DocenteActivity;
import com.dibapp.dibapp.home.StudenteActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText emailId, password;
    private Button btnSignIn;
    private TextView tvSignUp;
    private TextView forgotPsw;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button changeLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        btnSignIn = findViewById(R.id.buttonRegister);
        tvSignUp = findViewById(R.id.textView);
        forgotPsw = findViewById(R.id.passwordForg);
        changeLang = findViewById(R.id.buttonLang);

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fireBaseUser = firebaseAuth.getCurrentUser();
                if (fireBaseUser != null && fireBaseUser.isEmailVerified()) {
                    if(emailId.getText().toString().endsWith("@uniba.it")){
                        Toast.makeText(MainActivity.this, R.string.successful_login, Toast.LENGTH_SHORT).show();
                        Intent intToHome = new Intent(MainActivity.this, DocenteActivity.class);
                        startActivity(intToHome);
                    }
                    if(emailId.getText().toString().endsWith("@studenti.uniba.it")){
                        Toast.makeText(MainActivity.this, R.string.successful_login, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, R.string.campi_vuoti, Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    emailId.setError("Inserire l'indirizzo email");
                    emailId.requestFocus();
                } else if(psw.isEmpty()){
                    password.setError("Inserire la password");
                    password.requestFocus();
                }
                else if (!(email.isEmpty() && psw.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(email, psw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            } else {
                                if(email.endsWith("@studenti.uniba.it")){
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        Intent intToHome = new Intent(MainActivity.this, StudenteActivity.class);
                                        startActivity(intToHome);
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, R.string.verify, Toast.LENGTH_SHORT).show();
                                    }

                                }else if(email.endsWith("@uniba.it")){
                                    Intent intToHome = new Intent(MainActivity.this, DocenteActivity.class);
                                    startActivity(intToHome);
                                }
                            }
                        }
                    });
                } else
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
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

        changeLang.setOnClickListener(new View.OnClickListener(){
            //Mostra l'alertDialog per il cambio della lingua
            @Override
            public void onClick(View view){
                showChangeLanguageDialog();
            }
        });
    }

    //si creano differenti strings.xml per ognuna delle lingue
    private void showChangeLanguageDialog() {
        //array delle lingue da mostrare nella finestra di alert
        final String [] listItems = {"Italiano", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Seleziona la lingua..");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0) {
                    setLocale("it");
                    recreate();
                } else if(i==1){
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //salva dati per preferenze condivise
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("Mia_lingua", lang);
        editor.apply();
    }

    //carica lingua salvata nelle preferenze condivise
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("Mia_lingua", "");
        setLocale(language);
    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}