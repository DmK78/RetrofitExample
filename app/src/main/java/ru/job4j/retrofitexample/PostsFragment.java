package ru.job4j.retrofitexample;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import ru.job4j.retrofitexample.data.Post;

public class PostsFragment extends Fragment {
    private RecyclerView recycler;
    private PostsAdapter postAdapter;
    private List<Post> posts = new ArrayList<>();
    private OnPostClickListener callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_posts, container, false);
        recycler = view.findViewById(R.id.recyclerPosts);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Log.i("11111", String.format("Code is: %s", response.code()));
                }
                List<Post> postsFromUrl = response.body();
                Log.i("11111", String.format("posts size is: %s", postsFromUrl.size()));
                writePostsToRecycler(postsFromUrl);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.i("11111", t.getMessage());

            }
        });


        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnPostClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }

    private void writePostsToRecycler(List<Post> postsFromUrl) {
        posts.addAll(postsFromUrl);
        postAdapter = new PostsAdapter(getContext(), posts);
        this.recycler.setAdapter(postAdapter);
    }

    public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsHolder> {

        private List<Post> posts;
        private LayoutInflater inflater;

        PostsAdapter(Context context, List<Post> posts) {
            this.inflater = LayoutInflater.from(context);
            this.posts = posts;


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
                callback.onPostClicked(posts.get(getAdapterPosition()).getId());

            }
        }


    }

    public interface OnPostClickListener {
        void onPostClicked(int i);
    }

}
