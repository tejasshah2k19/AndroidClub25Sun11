package com.royal.androidclub25sun11;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookActivity extends AppCompatActivity {

    EditText edtName;
    EditText edtPrice;
    Spinner spinnerCategory;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //binding
        edtName = findViewById(R.id.edtBookName);
        edtPrice = findViewById(R.id.edtBookPrice);
        spinnerCategory = findViewById(R.id.spinnerBookCategory);
        btnSubmit = findViewById(R.id.btnBookSubmit);

        String categories[] = {"Select Category","Education:5%","Dev:8%","Ent:18%"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        spinnerCategory.setAdapter(adapter);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName = edtName.getText().toString();
                String strPrice = edtPrice.getText().toString();
                boolean isError = false;
                if(bookName.isBlank()){
                    edtName.setError("BookName required");
                    isError = true;
                }

                if(strPrice.isBlank()){
                    edtPrice.setError("Price required");
                    isError = true;
                }

                if(isError){
                    //
                    Toast.makeText(getApplicationContext(),"Please correct error(s)",Toast.LENGTH_LONG).show();
                }else{
                    //logic tax
                }
            }
        });

    }
}