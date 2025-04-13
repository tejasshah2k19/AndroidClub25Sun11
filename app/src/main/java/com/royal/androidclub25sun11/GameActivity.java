package com.royal.androidclub25sun11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameActivity extends AppCompatActivity {

    TextView tvUsername;
    TextView tvBetAmt;
    TextView tvWinningAmt;

    HashSet<Integer> bomArray;
    ImageButton imageButton[] = new ImageButton[16];

    Button btnCashOut;
    Integer winningAmt;
    Integer betAmt;
    String userId;
    String token;
    String firstName;

    String apiUrl = "https://diamondgame.onrender.com/api/users/credit/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        tvUsername = findViewById(R.id.tvGameUsername);
        tvBetAmt = findViewById(R.id.tvGameBetAmount);
        tvWinningAmt = findViewById(R.id.tvGameWinningAmount);



        betAmt = 500;
        winningAmt = 0;

        SharedPreferences preferences = getSharedPreferences("diamond_game",MODE_PRIVATE);
        firstName  = preferences.getString("firstName","USER");
        userId = preferences.getString("userId","-1");
        token = preferences.getString("token","-1");


        tvUsername.setText(firstName);
        tvWinningAmt.setText(winningAmt+"");
        tvBetAmt.setText(betAmt+"");

        imageButton[0] = findViewById(R.id.imgBtn0);
        imageButton[1] = findViewById(R.id.imgBtn1);
        imageButton[2] = findViewById(R.id.imgBtn2);
        imageButton[3] = findViewById(R.id.imgBtn3);
        imageButton[4] = findViewById(R.id.imgBtn4);
        imageButton[5] = findViewById(R.id.imgBtn5);
        imageButton[6] = findViewById(R.id.imgBtn6);
        imageButton[7] = findViewById(R.id.imgBtn7);
        imageButton[8] = findViewById(R.id.imgBtn8);
        imageButton[9] = findViewById(R.id.imgBtn9);
        imageButton[10] = findViewById(R.id.imgBtn10);
        imageButton[11] = findViewById(R.id.imgBtn11);
        imageButton[12] = findViewById(R.id.imgBtn12);
        imageButton[13] = findViewById(R.id.imgBtn13);
        imageButton[14] = findViewById(R.id.imgBtn14);
        imageButton[15] = findViewById(R.id.imgBtn15);

        bomArray = new HashSet<>();
        btnCashOut = findViewById(R.id.btnGamePlayCashOut);

        while(bomArray.size() != 4){
            int x = (int)(Math.random()*16);
            Log.i("GamePlay",x+"");
            bomArray.add(x);
        }


        for(int i=0;i<imageButton.length;i++){
            int index = i;
             imageButton[i].setOnClickListener(v->gamePlay(index));
        }

        btnCashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String winningAmtStr = tvWinningAmt.getText().toString();


                ExecutorService ex = Executors.newSingleThreadExecutor();

               Future<Integer> ft  =  ex.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return creditApi(userId,winningAmtStr,token);
                    }
                });


                try {
                    int resp =  ft.get();
                    Toast.makeText(getApplicationContext(),"Amount Updated",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(intent);

                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
        });


    }//

    private void gamePlay(int index){
        Log.i("GamePlay",index+"");

        Log.i("GamePlay",imageButton[index].getBackground().toString());

         if(bomArray.contains(index)){
            //game over -- bomb found
            imageButton[index].setBackground(getDrawable(R.drawable.boom));


             ExecutorService ex = Executors.newSingleThreadExecutor();

             Future<Integer> ft  =  ex.submit(new Callable<Integer>() {
                 @Override
                 public Integer call() throws Exception {

                     return creditApi(userId,betAmt*-1+"",token);
                 }
             });

             try {
                 ft.get();
                 Toast.makeText(getApplicationContext(),"Oops! Plz Try Again!!!",Toast.LENGTH_LONG).show();

                 Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                 startActivity(intent);

             } catch (ExecutionException e) {
                 throw new RuntimeException(e);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }

         }else {

             if(imageButton[index].getBackground().toString().contains("RippleDrawable")) {
                 winningAmt = winningAmt + betAmt;
                 tvWinningAmt.setText(winningAmt + "");
                 imageButton[index].setBackground(getDrawable(R.drawable.diamond));
             }

        }
    }

    private Integer creditApi(String userId,String winningAmtStr,String token){
        apiUrl = apiUrl + userId;

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("Authorization",token);

            Map<String,Integer> map = new HashMap<>();
            map.put("credit",Integer.parseInt(winningAmtStr));

            Gson gson = new Gson();
            String jsonString  = gson.toJson(map);

            OutputStream out = connection.getOutputStream();
            out.write(jsonString.getBytes());

            int resp = connection.getResponseCode();

            Log.i("CreditApi ",resp+"");

            return resp;
        }catch (Exception e){
            Log.i("error",e.getMessage());
        }
        return -1;
    }

}