package com.dibapp.dibapp.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dibapp.dibapp.R;

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
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView dateText;
        public TextView commentText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            dateText = (TextView) mView.findViewById(R.id.date_text);
            commentText = (TextView) mView.findViewById(R.id.comment_text);
        }
    }

}