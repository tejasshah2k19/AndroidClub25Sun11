package com.royal.androidclub25sun11;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.royal.androidclub25sun11.fragment.AddMovieFragment;
import com.royal.androidclub25sun11.fragment.ListMovieFragment;
import com.royal.androidclub25sun11.fragment.SearchMovieFragment;

public class MovieActivity extends AppCompatActivity {

    ImageButton imgBtnAdd;
    ImageButton imgBtnSearch;
    ImageButton imgBtnList;
    FrameLayout frameMaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBtnAdd = findViewById(R.id.imgBtnMovieAdd);
        imgBtnSearch = findViewById(R.id.imgBtnMovieSearch);
        imgBtnList = findViewById(R.id.imgBtnMovieList);
        frameMaster = findViewById(R.id.frameMovieMaster);

        //add movie fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameMovieMaster,new AddMovieFragment());
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();


        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgBtnAdd.setBackground(getResources().getDrawable(R.drawable.empty_add_box_24));
                imgBtnSearch.setBackground(getResources().getDrawable(R.drawable.fill_search));
                imgBtnList.setBackground(getApplicationContext().getDrawable(R.drawable.empty_list));
               //
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameMovieMaster,new SearchMovieFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgBtnAdd.setBackground(getResources().getDrawable(R.drawable.fill_add_box_18));
                imgBtnSearch.setBackground(getResources().getDrawable(R.drawable.empty_search_50));
                imgBtnList.setBackground(getApplicationContext().getDrawable(R.drawable.empty_list));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameMovieMaster,new AddMovieFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        imgBtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgBtnAdd.setBackground(getResources().getDrawable(R.drawable.empty_add_box_24));
                imgBtnSearch.setBackground(getResources().getDrawable(R.drawable.empty_search_50));
                imgBtnList.setBackground(getApplicationContext().getDrawable(R.drawable.fill_list_24));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameMovieMaster,new ListMovieFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



    }
}