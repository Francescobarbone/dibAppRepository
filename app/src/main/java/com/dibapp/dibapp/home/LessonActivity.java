package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class LessonActivity extends AppCompatActivity {

    private static final String TAG = "FireLog";
    private RecyclerView mMainList;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth firebaseAuth;
    private LessonsListAdapter lessonsListAdapter;
    private List<Lesson> lessonList;

    //admin per memorizzare le info
    private final User admin = new User();

    @Override
    public void onBackPressed(){
        if(firebaseAuth.getCurrentUser().getEmail().endsWith("@studenti.uniba.it"))
            startActivity(new Intent(LessonActivity.this, StudenteActivity.class));
        else
            startActivity(new Intent(LessonActivity.this, DocenteActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lezioni);

        lessonList = new ArrayList<>();
        lessonsListAdapter = new LessonsListAdapter(lessonList, getApplicationContext());

        mMainList = (RecyclerView)findViewById(R.id.lesson_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(lessonsListAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        String courseID = getIntent().getStringExtra("course_id");

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //utente in uso corrente
        admin.setEmail(user.getEmail());
        mFirestore = FirebaseFirestore.getInstance();


        if(firebaseAuth.getCurrentUser().getEmail().endsWith("@studenti.uniba.it")) {//visualizzazione lezioni per studente
            mFirestore.collection("Courses /" + courseID + "/Lessons").addSnapshotListener(LessonActivity.this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(TAG, "Error :" + e.getMessage());
                        return;
                    }
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            Lesson lesson = doc.getDocument().toObject(Lesson.class);
                            lessonList.add(lesson);
                            lessonsListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }else{//Visualizzazione delle lezioni per il docente
            mFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    boolean find = false;
                    for (DocumentSnapshot doc : task.getResult()) {
                        String email = doc.getString("email");
                        if (email.equals(admin.getEmail())) {
                            find = true;
                            //getting admin's courseID
                            admin.setCourseId(doc.getString("idCorso"));
                            break;
                        }
                    }
                    if(find){
                        mFirestore.collection("Courses /" + admin.getCourseId() + "/Lessons").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if(e!=null){
                                    Log.d(TAG, "Error :" + e.getMessage());
                                    return;
                                }
                                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                                    if(doc.getType() == DocumentChange.Type.ADDED){
                                        Lesson lesson = doc.getDocument().toObject(Lesson.class).withID(doc.getDocument().getId());
                                        lessonList.add(lesson);
                                        lessonsListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }

    }
}

