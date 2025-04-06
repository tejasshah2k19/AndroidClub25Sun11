package com.royal.androidclub25sun11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email= edtEmail.getText().toString();
                String password =  edtPassword.getText().toString();

                //api call ->
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

    private void loginApi(String email,String password){

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

           //200 401



        }catch (Exception e){
            Log.i("api",e.getMessage());
        }




    }

}//class