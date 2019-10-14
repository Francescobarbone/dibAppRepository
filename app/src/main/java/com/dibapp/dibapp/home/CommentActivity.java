package com.dibapp.dibapp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.dibapp.dibapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CommentActivity extends AppCompatActivity {

    private static final String TAG = "FireLog";
    private RecyclerView commentRecycler;
    private List<Comment> commentList;
    private CommentsListAdapter commentsListAdapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //get information from lessonListAdapter
        final String lessonID = getIntent().getStringExtra("lesson_id");
        final String courseID = getIntent().getStringExtra("course_id");

        //firebase info
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        commentList = new ArrayList<>();
        commentsListAdapter = new CommentsListAdapter(commentList, getApplicationContext());

        commentRecycler = (RecyclerView)findViewById(R.id.comment_list);
        commentRecycler.setHasFixedSize(true);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        commentRecycler.setAdapter(commentsListAdapter);

        firebaseFirestore.collection("Courses /" + courseID + "/Lessons/" + lessonID + "/Comments").addSnapshotListener(CommentActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(TAG, "Error: " + e.getMessage());
                    return;
                }
                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        Comment comment = doc.getDocument().toObject(Comment.class);
                        commentList.add(comment);
                        commentsListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
