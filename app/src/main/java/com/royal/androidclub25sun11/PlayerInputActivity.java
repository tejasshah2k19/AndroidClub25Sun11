package com.royal.androidclub25sun11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlayerInputActivity extends AppCompatActivity {

    Button btnPlay;
    EditText edtUsername;
    EditText edtBet;

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

        btnPlay = findViewById(R.id.btnInputPlay);
        edtUsername  = findViewById(R.id.edtInputUsername);
        edtBet = findViewById(R.id.edtInputBet);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = edtUsername.getText().toString();
                String betAmt =  edtBet.getText().toString();

                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("betAmt",betAmt);

                startActivity(intent);
            }
        });

    }
}