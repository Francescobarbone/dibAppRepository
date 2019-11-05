package com.dibapp.dibapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
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
import java.util.Locale;

//Activity per la creazione dei commenti
public class CommentCreateActivity extends AppCompatActivity {

    private TextView motivation;
    private FirebaseFirestore mFirestore;
    private Button saveComment;
    private RatingBar rates;
    private CheckBox anonymousComment;

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

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("Mia_lingua", "");
        setLocale(language);
    }


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
        loadLocale();

        rates = findViewById(R.id.ratingBar);
        saveComment = findViewById(R.id.buttonComment);
        motivation = findViewById(R.id.viewMotiv);
        anonymousComment = findViewById(R.id.checkBox);
        mFirestore = FirebaseFirestore.getInstance();

        //Ottengo informazioni da lessonListAdapter
        final String corso = getIntent().getStringExtra("courseid");
        final String userEmail = getIntent().getStringExtra("user");
        final String less = getIntent().getStringExtra("lessonid");


        rates.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                // set del commento in base alla valutazione
                switch (((int) rates.getRating())){
                    case 1:
                        motivation.setText(getString(R.string.pessima));
                        break;
                    case 2:
                        motivation.setText(getString(R.string.mediocre));
                        break;
                    case 3:
                        motivation.setText(getString(R.string.sufficiente));
                        break;
                    case 4:
                        motivation.setText(getString(R.string.buona));
                        break;
                    case 5:
                        motivation.setText(getString(R.string.ottima));
                        break;
                    default:
                        motivation.setText(getString(R.string.commento));
                        break;
                }


            }
        });

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rates.getRating() == 0.0) {
                    Toast.makeText(CommentCreateActivity.this, R.string.exp_valutazione, Toast.LENGTH_SHORT).show();
                } else{
                    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
                    Comment comment = new Comment();

                    //Controllo visibilità email
                    if(anonymousComment.isChecked())
                        comment = new Comment(motivation.getText().toString(), currentDate, less, "Anonimo", rates.getRating());
                    else
                        comment = new Comment(motivation.getText().toString(), currentDate, less,userEmail, rates.getRating());

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
