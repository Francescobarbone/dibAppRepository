package com.dibapp.dibapp.home;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;

import com.dibapp.dibapp.autenticazione.MainActivity;
import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class TeacherActivity extends HomeActivity {

    private Button showLess, createless, logOut, QR;
    private TextView welcomeTeacher;
    private FirebaseAuth firebaseAuth;
    private static User admin = new User();

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
        } else if(item.getItemId() == R.id.home){
            startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        showLess = findViewById(R.id.showLessons);
        createless = findViewById(R.id.creaLezione);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        welcomeTeacher = (TextView) findViewById(R.id.textView3);

        if(user!=null)
            welcomeTeacher.setText(user.getEmail().substring( 0, (user.getEmail().indexOf('@'))));

        mFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean find = false;
                for (DocumentSnapshot doc : task.getResult()) {
                    String email = doc.getString("email");
                    if (email.equals(admin.getEmail())) {
                        find = true;
                        //getting admin's courseID
                        admin.setCourseId(doc.getString("courseId"));
                        break;
                    }
                }
            }
        });



        showLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, LessonActivity.class));
            }
        });

        createless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, LessonCreateActivity.class));
            }
        });
        
    }
}
