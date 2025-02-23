package com.royal.androidclub25sun11;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddProductActivity extends AppCompatActivity {


    EditText edtProductName;
    EditText edtPrice;
    EditText edtQty;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //binding
        edtProductName = findViewById(R.id.edtAddProductName);
        edtPrice= findViewById(R.id.edtAddProductPrice);
        edtQty = findViewById(R.id.edtAddProductQty);
        btnSubmit  = findViewById(R.id.btnAddProductSubmit);

        //logic
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName =edtProductName.getText().toString();
                String strQty = edtQty.getText().toString();
                String strPrice = edtPrice.getText().toString();

                Log.i("product",productName);
                Log.i("product",strQty);//10
                Log.i("product",strPrice);//100000



            }
        });

    }
}