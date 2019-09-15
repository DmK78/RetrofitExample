package ru.job4j.retrofitexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsHolder> {
private MainActivity activity;
    private List<Post> posts;
    private LayoutInflater inflater;

    PostsAdapter(Context context, List<Post> posts, MainActivity mainActivity) {
        this.inflater = LayoutInflater.from(context);
        this.posts = posts;
        this.activity=mainActivity;

    }


    @NonNull
    @Override
    public PostsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.post_item, viewGroup, false);
        return new PostsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsHolder postsHolder, int i) {
        Post post = posts.get(i);
        postsHolder.textViewId.setText("" + post.getId());
        postsHolder.textViewUserId.setText("" + post.getUserId());
        postsHolder.textViewTitle.setText(post.getTitle());
        postsHolder.textViewText.setText(post.getText());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewId, textViewUserId, textViewTitle, textViewText;


        PostsHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.textViewPostId);
            textViewUserId = view.findViewById(R.id.textViewCommentId);
            textViewTitle = view.findViewById(R.id.textViewCommentsEmail);
            textViewText = view.findViewById(R.id.textViewText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Post post = posts.get(getAdapterPosition());
            Toast.makeText(v.getContext(), ""+post.getId(), Toast.LENGTH_SHORT).show();
activity.getCommentsFromURL(post.getId());

        }
    }



}
