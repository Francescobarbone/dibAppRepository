package com.dibapp.dibapp.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.Nullable;

//Activity per la visualizzazione delle lezioni
public class LessonActivity extends AppCompatActivity {

    private static final String TAG = "FireLog";
    private RecyclerView lessonRecycler;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth firebaseAuth;
    private LessonsListAdapter lessonsListAdapter;
    private List<Lesson> lessonList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("Mia_lingua", "");
        setLocale(language);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if(item.getItemId() == R.id.home){
            if(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).endsWith("@uniba.it"))
                startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
            else
                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //admin per memorizzare le info
    private final User admin = new User();

    @Override
    public void onBackPressed(){
        if(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).endsWith("@studenti.uniba.it"))
            startActivity(new Intent(LessonActivity.this, StudentActivity.class));
        else
            startActivity(new Intent(LessonActivity.this, TeacherActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        loadLocale();

        lessonList = new ArrayList<>();
        lessonsListAdapter = new LessonsListAdapter(lessonList, getApplicationContext());

        lessonRecycler = findViewById(R.id.lesson_list);
        lessonRecycler.setHasFixedSize(true);
        lessonRecycler.setLayoutManager(new LinearLayoutManager(this));
        lessonRecycler.setAdapter(lessonsListAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        String courseID = getIntent().getStringExtra("course_id");

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //utente in uso corrente
        if (user != null) {
            admin.setEmail(user.getEmail());
        }
        mFirestore = FirebaseFirestore.getInstance();

        //Visualizzazione lezioni per studente
        if(Objects.requireNonNull(firebaseAuth.getCurrentUser().getEmail()).endsWith("@studenti.uniba.it")) {
            mFirestore.collection("Courses /" + courseID + "/Lessons").orderBy("argument").get().addOnCompleteListener(LessonActivity.this, new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (Objects.requireNonNull(task.getResult()).isEmpty()) {
                        Toast.makeText(LessonActivity.this, R.string.no_less, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for(QueryDocumentSnapshot doc : task.getResult()) {
                        Lesson lesson = doc.toObject(Lesson.class).withID(doc.getId());
                        lessonList.add(lesson);
                        lessonsListAdapter.notifyDataSetChanged();
                    }
                }
            });
            //Visualizzazione delle lezioni per il docente
        }else{
            mFirestore.collection("Users").get().addOnCompleteListener(LessonActivity.this, new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    boolean find = false;
                    for (DocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                        String email = doc.getString("email");
                        if (Objects.equals(email, admin.getEmail())) {
                            find = true;
                            //getting admin's courseID
                            admin.setCourseId(doc.getString("idCorso"));
                            break;
                        }
                    }
                    if(find){
                        mFirestore.collection("Courses /" + admin.getCourseId() + "/Lessons").orderBy("argument").get().addOnCompleteListener(LessonActivity.this, new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (Objects.requireNonNull(task.getResult()).isEmpty()) {
                                    Toast.makeText(LessonActivity.this, R.string.no_less, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                for(QueryDocumentSnapshot doc : task.getResult()) {
                                    Lesson lesson = doc.toObject(Lesson.class).withID(doc.getId());
                                    lessonList.add(lesson);
                                    lessonsListAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}

