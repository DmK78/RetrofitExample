package ru.job4j.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private RecyclerView recycler;
    private PostsAdapter postAdapter;
    private CommentsAdapter commentsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recyclerMain);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getPostsFromURL();
        //getCommentsFromURL();
        //createPost(new Post(1,"title1", "text1"));
    }

    private void createPost(final Post post){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Map<String, String> fields = new HashMap<>();


        Call<Post> call = jsonPlaceHolderApi.putPost(1,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    List<Post> postList = new ArrayList<>();
                    postList.add(response.body());
                    writePostsToRecycler(postList);
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }





    private void getPostsFromURL() {
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


    }

    public void getCommentsFromURL(int id) {
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


    }

    private void writeCommentsToRecycler(List<Comment> commentsFromUrl) {
        comments.addAll(commentsFromUrl);
        commentsAdapter = new CommentsAdapter(getApplicationContext(), comments);
        this.recycler.setAdapter(commentsAdapter);
    }

    private void writePostsToRecycler(List<Post> postsFromUrl) {
        posts.addAll(postsFromUrl);
        postAdapter = new PostsAdapter(getApplicationContext(), posts, MainActivity.this);
        this.recycler.setAdapter(postAdapter);
    }


}
