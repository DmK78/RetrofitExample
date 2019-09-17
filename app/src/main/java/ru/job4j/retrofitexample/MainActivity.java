package ru.job4j.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements PostsFragment.OnPostClickListener {
    private FragmentManager fm;
    private androidx.fragment.app.Fragment postsFragment;
    private Fragment commentsFragment;
    public static String POST_ID = "postId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager(); // получить FragmentManager
        postsFragment = fm.findFragmentById(R.id.frameContainer);
        if (postsFragment == null) {
            postsFragment = new PostsFragment();
            fm.beginTransaction()
                    .add(R.id.frameContainer, postsFragment) // добавить фрагмент в контейнер
                    .commit();
        }
    }

    @Override
    public void onPostClicked(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(POST_ID, i);
        commentsFragment = new CommentsFragment();
        commentsFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.frameContainer, commentsFragment)
                .addToBackStack(null)
                .commit();
    }


}
