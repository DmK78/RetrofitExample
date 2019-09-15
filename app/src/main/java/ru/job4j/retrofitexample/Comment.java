package ru.job4j.retrofitexample;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private int postId;
    private Integer id;
    private String email;

    @SerializedName("body")
    private String text;

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
