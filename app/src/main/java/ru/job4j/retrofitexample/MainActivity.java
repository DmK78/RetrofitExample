package ru.job4j.retrofitexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private List<Post> posts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.texViewResult);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call <List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText(String.format("Caode is: %s", response.code()));
                }
                List<Post> postsFromUrl = response.body();
                posts.addAll(postsFromUrl);
                /*for (Post post : posts) {
                    String content = String.format("ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n", post.getId(),
                            post.getUserId(), post.getTitle(), post.getText());
                    textViewResult.append(content);
                }*/

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });
                for (Post post : posts) {
                    String content = String.format("ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n", post.getId(),
                            post.getUserId(), post.getTitle(), post.getText());
                    textViewResult.append(content);
                }





    }


}
