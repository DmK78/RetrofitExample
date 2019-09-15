package ru.job4j.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts();
    @GET("comments")
    Call<List<Comment>> getComments();
    @GET
    Call<List<Comment>> getComments(@Url String url);
    @GET("posts/{id}")
    Call<Post> getPost (@Path("id") int posId);
    @GET("comments/{id}")
    Call<Comment> getComment (@Path("id") int posId);
}

