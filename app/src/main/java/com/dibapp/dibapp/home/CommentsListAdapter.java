package com.dibapp.dibapp.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {


    public List<Comment> commentList;
    public Context context;

    public CommentsListAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateText.setText(commentList.get(position).getTimestamp());
        holder.commentText.setText(commentList.get(position).getMessage());
        holder.userString.setText(commentList.get(position).getUserComment());
        holder.rating.setNumStars((int)commentList.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        if(commentList != null)
            return commentList.size();
        else
            return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView dateText;
        public TextView commentText;
        public TextView userString;
        public RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            dateText = (TextView) mView.findViewById(R.id.date_text);
            commentText = (TextView) mView.findViewById(R.id.comment_text);
            userString = (TextView) mView.findViewById(R.id.user_text);
            rating = (RatingBar) mView.findViewById(R.id.ratingBar);
        }
    }

}