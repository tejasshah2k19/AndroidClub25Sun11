package com.royal.androidclub25sun11;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.royal.androidclub25sun11.model.UserModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.concurrent.Future;

public class LeaderboardActivity extends AppCompatActivity {

    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listViewLeaderboard);

        ExecutorService ex = Executors.newSingleThreadExecutor();

       Future<ArrayList<UserModel>> ft =  ex.submit(new Callable<ArrayList<UserModel>>() {
            @Override
            public ArrayList<UserModel> call() throws Exception {
                return leaderBoardApi();
            }
        });

        try {
            ArrayList<UserModel> leader =  ft.get();
            ArrayAdapter<UserModel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leader);
            listView.setAdapter(adapter);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    private ArrayList<UserModel> leaderBoardApi(){
        ArrayList<UserModel> list = new ArrayList<>();

        String apiUrl = "https://diamondgame2.onrender.com/api/users/leaderboard";

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
             connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");


            InputStream inputStream =  connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer apiResp = new StringBuffer();
            String str;
            while ( ( str  = bufferedReader.readLine()) != null){
                apiResp.append(str);
            }

            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<UserModel>>() {}.getType();

            list =  gson.fromJson(apiResp.toString(),userListType);


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return list;
    }
}