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

import com.dibapp.dibapp.QRCodeScanner;
import com.dibapp.dibapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;
import java.util.Objects;


public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {

    private List<Lesson> lessonList;
    public Context context;
    private final User admin = new User();

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

        //Firebase info
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        admin.setEmail(Objects.requireNonNull(user).getEmail());

        holder.dayText.setText(lessonList.get(i).getLessonDate());
        holder.argText.setText(lessonList.get(i).getArgument());

        //Ottengo info da courseListAdapter
        final String courseID = lessonList.get(i).getIdCourse();
        final String lessID = lessonList.get(i).getLessonID();
        final String nameLess = lessonList.get(i).getArgument();
        final int position = i;

       holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("course_id", courseID);
                intent.putExtra("lesson_id", lessID);
                context.startActivity(intent);
            }
        });

        holder.delete.setVisibility(View.GONE);
        holder.create.setVisibility(View.GONE);

        //Se studente, visualizza l'icona per effettuare il commento
        if(Objects.requireNonNull(firebaseAuth.getCurrentUser().getEmail()).endsWith("@studenti.uniba.it")){
            holder.create.setVisibility(View.VISIBLE);
            holder.create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebaseFirestore.collection("Courses /" + courseID + "/Lessons/" + lessID + "/Comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            boolean flag = false;
                            for (DocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                String email = doc.getString("userComment");

                                if (Objects.equals(email, admin.getEmail())) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                Intent intent = new Intent(context, QRCodeScanner.class);
                                intent.putExtra("course_id", courseID);
                                intent.putExtra("lesson_id", lessID);
                                intent.putExtra("name_lesson", nameLess);
                                intent.putExtra("userMail", admin.getEmail());
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, R.string.commento_effettuato, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        }
//Se docente, visualizza l'icona per effettuare la cancellazione della lezione
        firebaseFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean find = false;
                        for(DocumentSnapshot doc : Objects.requireNonNull(task.getResult())){
                            String email = doc.getString("email");

                            if(Objects.equals(email, admin.getEmail())) {
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
                                 Toast.makeText(context, "Lezione eliminata", Toast.LENGTH_SHORT).show();
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

        private TextView dayText;
        private TextView argText;
        private ImageView delete;
        private ImageView create;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            dayText = mView.findViewById(R.id.time_text);
            argText = mView.findViewById(R.id.arg_text);
            delete = mView.findViewById(R.id.ic_delete);
            create = mView.findViewById(R.id.ic_create);
        }
    }
}
