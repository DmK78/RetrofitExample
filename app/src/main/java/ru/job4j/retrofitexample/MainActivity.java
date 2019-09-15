package ru.job4j.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private List<Post> posts = new ArrayList<>();
    private RecyclerView recycler;
    private PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recyclerMain);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getPostsFromURL();
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

    private void writePostsToRecycler(List<Post> postsFromUrl) {
        posts.addAll(postsFromUrl);
        adapter = new PostsAdapter(getApplicationContext(), posts);
        this.recycler.setAdapter(adapter);
    }
}
