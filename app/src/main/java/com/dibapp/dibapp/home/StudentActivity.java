package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentActivity extends HomeActivity {

    private Button visualizzaLezione;
    private Button seguiLezione;
    private TextView textViewBenvenuto;
    private FirebaseAuth firebaseAuth;

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
        setContentView(R.layout.activity_student);
        visualizzaLezione = findViewById(R.id.butLezione);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewBenvenuto = (TextView) findViewById(R.id.benvenutoId);
        textViewBenvenuto.setText(user.getEmail().substring(0, (user.getEmail().indexOf('@'))));


        //Visualizzazione dei corsi, lezione e commenti
        visualizzaLezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studToQR = new Intent(StudentActivity.this, CourseActivity.class);
                startActivity(studToQR);
            }
        });

    }
}
