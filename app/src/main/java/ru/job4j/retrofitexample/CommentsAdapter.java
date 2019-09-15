package ru.job4j.retrofitexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder>{
    private List<Comment> comments;
    private LayoutInflater inflater;

    CommentsAdapter(Context context, List<Comment> comments) {
        this.inflater = LayoutInflater.from(context);
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsAdapter.CommentsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.comment_item, viewGroup, false);
        return new CommentsAdapter.CommentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.CommentsHolder postsHolder, int i) {
        Comment comment = comments.get(i);
        postsHolder.textViewCommentId.setText(""+comment.getId());
        postsHolder.textViewPostId.setText(""+comment.getPostId());
        postsHolder.textViewEmail.setText(comment.getEmail());
        postsHolder.textViewCommentText.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewCommentId, textViewPostId, textViewEmail, textViewCommentText;


        CommentsHolder(View view) {
            super(view);
            textViewCommentId = view.findViewById(R.id.textViewCommentId);
            textViewPostId = view.findViewById(R.id.textViewPostId);
            textViewEmail = view.findViewById(R.id.textViewCommentsEmail);
            textViewCommentText = view.findViewById(R.id.textViewCommentText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }
}
