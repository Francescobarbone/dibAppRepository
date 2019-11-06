package com.dibapp.dibapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

//Activity per la visualizzazione dei commenti
public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecycler;
    private List<Comment> commentList;
    private CommentsListAdapter commentsListAdapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView textStudent;
    private TextView numOfStudent;

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
            if(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).endsWith("@uniba.it"))
                startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
            else
                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        textStudent = findViewById(R.id.countStudenti);
        numOfStudent = findViewById(R.id.numStudenti);
        numOfStudent.setVisibility(View.INVISIBLE);

        //Ottengo informazioni da lessonListAdapter
        final String lessonID = getIntent().getStringExtra("lesson_id");
        final String courseID = getIntent().getStringExtra("course_id");

        //firebase info
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //Lista dei commenti
        commentList = new ArrayList<>();
        commentsListAdapter = new CommentsListAdapter(commentList, getApplicationContext());

        commentRecycler = findViewById(R.id.comment_list);
        commentRecycler.setHasFixedSize(true);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        commentRecycler.setAdapter(commentsListAdapter);


        firebaseFirestore.collection("Courses /" + courseID + "/Lessons/" + lessonID + "/Comments").get().addOnCompleteListener(CommentActivity.this, new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (Objects.requireNonNull(task.getResult()).isEmpty()) {
                    Toast.makeText(CommentActivity.this, R.string.no_comm, Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    for(QueryDocumentSnapshot doc : task.getResult()){
                            Comment comment = doc.toObject(Comment.class);
                            commentList.add(comment);
                            commentsListAdapter.notifyDataSetChanged();

                }
            }
                //Visualizza il numero dei partecipanti
                if(commentList != null) {
                    numOfStudent.setVisibility(View.VISIBLE);
                    textStudent.setText(": " + commentsListAdapter.getItemCount());
                }
        }
        });
    }
}
