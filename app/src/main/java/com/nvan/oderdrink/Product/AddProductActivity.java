package com.nvan.oderdrink.Product;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.R;

public class AddProductActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private EditText editTextProductID;
    private EditText editTextProductPrice;
    private EditText editTextProductImageURL;
    private EditText editTextProductCategory;
    private EditText editTextProductDesc;
    private Button buttonAddProduct;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductID = findViewById(R.id.editTextProductID);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        editTextProductImageURL = findViewById(R.id.editTextProductImageURL);
        editTextProductCategory = findViewById(R.id.editTextCategory);
        editTextProductDesc = findViewById(R.id.editTextDetail);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);

        databaseReference = FirebaseDatabase.getInstance().getReference("Drinks");

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin sản phẩm từ các trường EditText
                String productName = editTextProductName.getText().toString();
                String productID = editTextProductID.getText().toString();
                String productCate = editTextProductCategory.getText().toString();
                String productDesc = editTextProductDesc.getText().toString();
                int productPrice = Integer.parseInt(editTextProductPrice.getText().toString());
                String productImageURL = editTextProductImageURL.getText().toString();

                // Tạo đối tượng Product
                Drinks product = new Drinks(productName, productID, productImageURL, productPrice,productCate,productDesc);

                // Lưu thông tin sản phẩm vào Firebase Realtime Database
                databaseReference.child(productID).setValue(product);

                // Đóng hoạt động và trả về kết quả thành công
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}