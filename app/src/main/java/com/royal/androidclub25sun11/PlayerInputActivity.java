package com.royal.androidclub25sun11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlayerInputActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtEmail;
    EditText edtPassword;

    TextView tvNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLogin = findViewById(R.id.btnInputLogin);
        edtEmail  = findViewById(R.id.edtInputEmail);
        edtPassword = findViewById(R.id.edtInputPassword);
        tvNewUser = findViewById(R.id.tvInputSignup);

        edtEmail.setText("vatsal@yopmail.com");
        edtPassword.setText("vatsal");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email= edtEmail.getText().toString();
                String password =  edtPassword.getText().toString();


                ExecutorService ex = Executors.newSingleThreadExecutor();

               Future<Integer> ft =  ex.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return loginApi(email,password);
                    }
                });

                try {
                    Integer respCode = ft.get();
                    if(respCode == 200)
                    {
                            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                            startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }

                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
        });

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //animation
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });




    }//onCreate

    private Integer loginApi(String email,String password){

        String apiUrl = "https://diamondgame.onrender.com/api/auth/login";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection =  (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            HashMap<String,String> map = new HashMap<>();
            map.put("email",email);
            map.put("password",password);

            Gson gson = new Gson() ;
            String jsonString  = gson.toJson(map);

            OutputStream out =  connection.getOutputStream();
            out.write(jsonString.getBytes());
            out.flush();
            out.close();

           Integer statusCode = connection.getResponseCode();
            Log.i("api",statusCode+"");



            if (statusCode == HttpURLConnection.HTTP_OK) {

                Log.i("api","done");
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                Log.i("api","api response => "+response.toString());

                JSONObject jsonObjectResp = new JSONObject(response.toString());
                JSONObject user = jsonObjectResp.getJSONObject("user");


                SharedPreferences preferences = getSharedPreferences("diamond_game",MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("firstName",user.getString("firstName"));
                editor.putString("lastName",user.getString("lastName"));
                editor.putInt("credit",user.getInt("credit"));
                editor.putString("userId",user.getString("_id"));
                editor.putString("token",jsonObjectResp.getString("token"));
                editor.apply(); //save

                return 200;


            }else{
                Log.i("api","fail");
            }

        }catch (Exception e){
            Log.i("api","error => "+e.getMessage());
        }


        return -1;

    }

}//class