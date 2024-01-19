package com.nvan.oderdrink.Product;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.R;

public class EditProductActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productDescText;
    private EditText productImageURLEditText;
    private Button updateButton;

    private DatabaseReference productRef;
    private Drinks product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Lấy đối tượng Product từ Intent
        product = getIntent().getParcelableExtra("product");

        // Khởi tạo DatabaseReference để truy cập vào sản phẩm cần chỉnh sửa
        productRef = FirebaseDatabase.getInstance().getReference("Drinks").child(product.getId());

        // Ánh xạ các View
        productNameEditText = findViewById(R.id.edit_TextProductName);
        productPriceEditText = findViewById(R.id.edit_TextProductPrice);
        productDescText = findViewById(R.id.edit_TextDetail);
        productImageURLEditText = findViewById(R.id.edit_TextProductImageURL);
        updateButton = findViewById(R.id.buttonEditProduct);

        // Lắng nghe sự kiện khi nhấn nút "Cập nhật"
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });

        // Đổ dữ liệu sản phẩm vào các trường EditText tương ứng
        if (product != null) {
            productNameEditText.setText(product.getName());
            productPriceEditText.setText(String.valueOf(product.getPrice()));
            productImageURLEditText.setText(product.getImage());
            productDescText.setText(product.getDescreption());
        }
    }

    private void updateProduct() {
        // Lấy giá trị từ các trường EditText
        String productName = productNameEditText.getText().toString().trim();
        String productPriceString = productPriceEditText.getText().toString().trim();
        String productDesc = productDescText.getText().toString().trim();
        String productImageURL = productImageURLEditText.getText().toString().trim();

        if (productName.isEmpty() || productPriceString.isEmpty() || productImageURL.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển đổi giá sản phẩm từ String sang int
        int productPrice = Integer.parseInt(productPriceString);

        // Cập nhật thông tin sản phẩm vào Firebase Database
        productRef.child("name").setValue(productName);
        productRef.child("price").setValue(productPrice);
        productRef.child("descreption").setValue(productDesc);
        productRef.child("image").setValue(productImageURL)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProductActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(EditProductActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
//public class EditProductActivity extends AppCompatActivity {
//
//    private EditText productNameEditText;
//    private EditText productPriceEditText;
////    private EditText productCateText;
//    private EditText productDescText;
//    private EditText productImageURLEditText;
//    private Button updateButton;
//
//    private DatabaseReference productRef;
//    private Drinks product;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_product);
//
//        // Lấy đối tượng Product từ Intent
//        product = getIntent().getParcelableExtra("product");
//
//        // Khởi tạo DatabaseReference để truy cập vào sản phẩm cần chỉnh sửa
//        productRef = FirebaseDatabase.getInstance().getReference("Drinks").child(product.getId());
//
//        // Ánh xạ các View
//        productNameEditText = findViewById(R.id.edit_TextProductName);
//        productPriceEditText = findViewById(R.id.edit_TextProductPrice);
//        productDescText = findViewById(R.id.edit_TextDetail);
//        productImageURLEditText = findViewById(R.id.edit_TextProductImageURL);
//        updateButton = findViewById(R.id.buttonEditProduct);
//
//        // Lắng nghe sự kiện khi nhấn nút "Cập nhật"
//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateProduct();
//            }
//        });
//
//        // Đổ dữ liệu sản phẩm vào các trường EditText tương ứng
//        if (product != null) {
//            productNameEditText.setText(product.getName());
//            productPriceEditText.setText(String.valueOf(product.getPrice()));
//            productDescText.setText(product.getDescreption());
//            productImageURLEditText.setText(product.getImage());
//        }
//    }
//
//    private void updateProduct() {
//        // Lấy giá trị từ các trường EditText
//        String productName = productNameEditText.getText().toString().trim();
//        String productPriceString = productPriceEditText.getText().toString().trim();
//        String productDesc = productDescText.getText().toString().trim();
//        String productImageURL = productImageURLEditText.getText().toString().trim();
//
//        if (productName.isEmpty() || productPriceString.isEmpty() || productImageURL.isEmpty()) {
//            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Chuyển đổi giá sản phẩm từ String sang Double
//        int productPrice = Integer.parseInt(productPriceString);
//
//        // Cập nhật thông tin sản phẩm vào Firebase Database
//        productRef.child("name").setValue(productName);
//        productRef.child("price").setValue(productPrice);
//        productRef.child("descreption").setValue(productDesc);
//        productRef.child("image").setValue(productImageURL)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(EditProductActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                            setResult(RESULT_OK);
//                            finish();
//                        } else {
//                            Toast.makeText(EditProductActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//}
