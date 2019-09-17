package ru.job4j.retrofitexample;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.job4j.retrofitexample.data.Comment;

public class CommentsFragment extends Fragment {
    private RecyclerView recycler;
    private List<Comment> comments = new ArrayList<>();
    private CommentsAdapter commentsAdapter;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_posts, container, false);
        if (getArguments() != null) {
            id = getArguments().getInt(MainActivity.POST_ID);
        }
        recycler = view.findViewById(R.id.recyclerPosts);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Comment>> call = jsonPlaceHolderApi.getCommentsById(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    Log.i("11111", String.format("Code is: %s", response.code()));
                }
                List<Comment> commentsFromUrl = response.body();
                Log.i("11111", String.format("comments size is: %s", commentsFromUrl.size()));
                writeCommentsToRecycler(commentsFromUrl);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.i("11111", t.getMessage());

            }
        });


        return view;
    }

    private void writeCommentsToRecycler(List<Comment> commentsFromUrl) {
        comments.addAll(commentsFromUrl);
        commentsAdapter = new CommentsAdapter(getContext(), comments);
        this.recycler.setAdapter(commentsAdapter);
    }

    public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {
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
            postsHolder.textViewCommentId.setText("" + comment.getId());
            postsHolder.textViewPostId.setText("" + comment.getPostId());
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


}
