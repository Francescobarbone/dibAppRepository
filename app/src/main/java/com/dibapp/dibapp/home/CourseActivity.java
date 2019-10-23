package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

//Activity per la visualizzazione dei corsi
public class CourseActivity extends AppCompatActivity {

    private RecyclerView mMainList;
    private FirebaseFirestore mFirestore;
    private List<Course> courseList;
    private CoursesListAdapter courseListAdapter;

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
                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        //Lista corsi
        courseList = new ArrayList<>();
        courseListAdapter = new CoursesListAdapter(getApplicationContext(), courseList);

        mMainList = findViewById(R.id.main_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(courseListAdapter);
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("Courses ").addSnapshotListener(CourseActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : Objects.requireNonNull(documentSnapshots).getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        Course course = doc.getDocument().toObject(Course.class).withId(doc.getDocument().getId());
                        courseList.add(course);
                        courseListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
