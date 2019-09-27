package com.dibapp.dibapp.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView mMainList;
    private FirebaseFirestore mFirestore;
    private List<Course> courseList;
    private CoursesListAdapter courseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corsi);

        courseList = new ArrayList<>();
        courseListAdapter = new CoursesListAdapter(getApplicationContext(), courseList);

        mMainList = (RecyclerView) findViewById(R.id.main_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(courseListAdapter);
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Courses ").addSnapshotListener(CourseActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
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
