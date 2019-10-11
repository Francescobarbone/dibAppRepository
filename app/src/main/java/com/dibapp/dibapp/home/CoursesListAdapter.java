package com.dibapp.dibapp.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;

import java.util.List;

public class CoursesListAdapter extends RecyclerView.Adapter<CoursesListAdapter.ViewHolder> {

    private List<Course> courseList;
    public Context contextCourse;

    public CoursesListAdapter(Context contextCourse, List<Course> courseList){
        this.courseList = courseList;
        this.contextCourse = contextCourse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_courses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameText.setText(courseList.get(position).getCourseName());
        holder.dateText.setText(courseList.get(position).getCourseTime());

        final String course_id = courseList.get(position).getCourseID();
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent courseToLess = new Intent(contextCourse, LessonActivity.class);
                courseToLess.putExtra("course_id", course_id);
                contextCourse.startActivity(courseToLess);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView nameText;
        public TextView dateText;

        public ViewHolder(View itemView){
            super(itemView);
            mView = itemView;

            nameText = (TextView) mView.findViewById(R.id.name_view);
            dateText = (TextView) mView.findViewById(R.id.course_date);
        }
    }
}
