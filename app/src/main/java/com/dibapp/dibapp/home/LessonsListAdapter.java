package com.dibapp.dibapp.home;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {

    private List<Lesson> lessonList;
    public Context contextLess;

    public LessonsListAdapter(List<Lesson> lessonList, Context context){
        this.lessonList = lessonList;
        this.contextLess = contextLess;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lessons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayText.setText(lessonList.get(position).getLessonDate());
        holder.argText.setText(lessonList.get(position).getArgument());
        final String less_id = lessonList.get(position).lessonID;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(contextLess, "Less ID: " + less_id, Toast.LENGTH_SHORT).show();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            dayText = (TextView) mView.findViewById(R.id.day_text);
            argText = (TextView) mView.findViewById(R.id.arg_text);
        }
    }
}
