package com.dibapp.dibapp.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {

    public List<Lesson> lessonList;
    public Context context;
    final User admin = new User();

    public LessonsListAdapter(List<Lesson> lessonList, Context context){
        this.lessonList = lessonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lessons, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        admin.setEmail(user.getEmail());

        holder.dayText.setText(lessonList.get(i).getLessonDate());
        holder.argText.setText(lessonList.get(i).getArgument());

        final String lessID = lessonList.get(i).getLessonID();
        final int position = i;

       /* holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("lesson_id", lessID);
                context.startActivity(intent);
            }
        });*/

        holder.delete.setVisibility(View.INVISIBLE);
        firebaseFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean find = false;
                        for(DocumentSnapshot doc : task.getResult()){
                            String email = doc.getString("email");

                            if(email.equals(admin.getEmail())) {
                                find = true;
                                //getting admin's courseID
                                admin.setCourseId(doc.getString("idCorso"));
                                break;
                            }
                        }
            if (find){
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          firebaseFirestore.document("Courses /" + admin.getCourseId() + "/Lessons/" + lessID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                 Toast.makeText(context, firebaseFirestore.document("Courses /" + admin.getCourseId() + "/Lessons/" + lessID).getPath().toString(), Toast.LENGTH_SHORT).show();
                                 lessonList.remove(position);
                                 notifyDataSetChanged();
                              }
                            });
                        }
                });
            } else holder.delete.setVisibility(View.GONE);
        }
    });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView dayText;
        public TextView argText;
        public ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            dayText = (TextView) mView.findViewById(R.id.time_text);
            argText = (TextView) mView.findViewById(R.id.arg_text);
            delete = (ImageView) mView.findViewById(R.id.ic_delete);
        }
    }
}
