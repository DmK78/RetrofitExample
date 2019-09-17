package ru.job4j.retrofitexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ErrorActivity extends AppCompatActivity {
    TextView textViewError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        textViewError = findViewById(R.id.textViewError);

            textViewError.setText("ERROR CODE IS: " + getIntent().getIntExtra(PostsFragment.ERROR_CODE, -1));


    }
}
