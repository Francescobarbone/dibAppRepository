package com.dibapp.dibapp.home;

import android.content.Intent;
import android.os.Bundle;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class LezioniDocenteActivity extends AppCompatActivity {

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
        startActivity(new Intent(LezioniDocenteActivity.this, DocenteActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lezioni_docente);

        lessonList = new ArrayList<>();
        lessonsListAdapter = new LessonsListAdapter(lessonList, getApplicationContext());

        mMainList = (RecyclerView)findViewById(R.id.lesson_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(lessonsListAdapter);
        firebaseAuth = FirebaseAuth.getInstance();

        //utente in uso corrente
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        admin.setEmail(user.getEmail());
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("Users").get().addOnCompleteListener(LezioniDocenteActivity.this, new OnCompleteListener<QuerySnapshot>() {
            String docID = null;
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                boolean flag = false;
                for(QueryDocumentSnapshot doc : task.getResult()){
                    String email = doc.getString("email");
                    if(email.equals(admin.getEmail())){
                        flag = true;
                        admin.setCourseId(doc.getString("idCorso"));
                        docID = admin.getCourseId();
                        break;
                    }
                }
                if(flag)
                    mFirestore.collection("Courses /" + docID + "/Lessons").addSnapshotListener(new EventListener<QuerySnapshot>() {

                        public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                            for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                                if(doc.getType() == DocumentChange.Type.ADDED){
                                    Lesson lessons = doc.getDocument().toObject(Lesson.class).withID(doc.getDocument().getId());
                                    lessonList.add(lessons);
                                    lessonsListAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
            }
        });


    }
}
