package com.royal.androidclub25sun11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SignupActivity extends AppCompatActivity {

    EditText edtFirstName;
    EditText edtEmail;
    EditText edtPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    edtFirstName = findViewById(R.id.edtSignupFirstName);
    edtEmail = findViewById(R.id.edtSignupEmail);
    edtPassword =findViewById(R.id.edtSignupPassword);
    btnSignup = findViewById(R.id.btnSignupSubmit);


    btnSignup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String firstName = edtFirstName.getText().toString();
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            boolean isError = false;
            if(firstName.isBlank()){
                isError = true;
                edtFirstName.setError("Firstname is required");
            }

            if(email.isBlank()){
                isError = true;
                edtEmail.setError("Email is required");
            }

            if(password.isBlank()){
                isError = true;
                edtPassword.setError("Password is required");
            }

            if(isError){
                Toast.makeText(getApplicationContext(), "Please Correct Error(s)", Toast.LENGTH_LONG).show();
            }else{
                //call api


                //

               ExecutorService ex =  Executors.newSingleThreadExecutor();

              Future<Integer> ft  = ex.submit(new Callable<Integer>() {
                   @Override
                   public Integer call() throws Exception {
                       return signupApi(firstName,email,password);
                   }
               });


               // Executors.newCachedThreadPool();
               // Executors.newFixedThreadPool(5);

                try {
                  Integer statusCode = ft.get();
                  if(statusCode == 201){
                      //signup done
                      //redirect to login page
                      Log.i("API","success");

                      Intent intent = new Intent(getApplicationContext(),PlayerInputActivity.class);
                      startActivity(intent);
                      
                  }
                  Log.i("API",statusCode+"");
              }catch (Exception e){
                  e.printStackTrace();

                  Log.i("API","ERROR ");
              }


            }
        }
    });



    }//create


    private Integer signupApi(String firstName,String email,String password){
        //201
        //400 500


        HashMap<String,Object> map = new HashMap<>();
        map.put("firstName",firstName);
        map.put("email",email);
        map.put("password",password);
        map.put("lastName","NA");
        map.put("credit",5000);

        Gson gson = new Gson();
        String jsonStr =  gson.toJson(map);
        Log.i("API",jsonStr);
        try {
            URL url = new URL("https://diamondgame.onrender.com/api/auth/signup");

              HttpURLConnection connection = (HttpURLConnection) url.openConnection();
              connection.setRequestMethod("POST");
              connection.setRequestProperty("Content-Type","application/json");
              connection.setRequestProperty("Accept", "application/json");
              connection.setDoOutput(true);
              connection.setDoInput(true);

            OutputStream os = connection.getOutputStream();
            os.write(jsonStr.getBytes());
            os.flush();
            os.close();

            int resp = connection.getResponseCode();
            Log.i("API",resp+" resp code");
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            Log.i("API",response.toString());
            return resp;

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("API","ERROR "+e.getMessage());
        }
        return -1;
    }
}