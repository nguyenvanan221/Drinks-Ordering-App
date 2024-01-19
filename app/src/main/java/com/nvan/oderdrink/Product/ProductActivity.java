package com.nvan.oderdrink.Product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.R;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Drinks> productList;
    private Button addButton;

    private static final int ADD_PRODUCT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.ProductRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(ProductActivity.this, productList);
        recyclerView.setAdapter(productAdapter);

        addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang AddProductActivity và chờ kết quả trả về
                Intent intent = new Intent(ProductActivity.this, AddProductActivity.class);
                startActivityForResult(intent, ADD_PRODUCT_REQUEST_CODE);
            }
        });

        // Lấy dữ liệu sản phẩm từ Firebase Realtime Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Drinks");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String productId = snapshot.getKey();
                    String productName = snapshot.child("name").getValue(String.class);
                    String productImage = snapshot.child("image").getValue(String.class);
                    String productCategory = snapshot.child("category").getValue(String.class);
                    String productDesc = snapshot.child("descreption").getValue(String.class);
                    int productPrice = snapshot.child("price").getValue(Integer.class);

                    Drinks product = new Drinks(productName, productId, productImage, productPrice,productCategory,productDesc);
                    productList.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PRODUCT_REQUEST_CODE) {
            // Kiểm tra kết quả trả về từ AddProductActivity
            if (resultCode == RESULT_OK) {
                // Cập nhật lại danh sách sản phẩm
                productList.clear();
                // Lấy dữ liệu sản phẩm từ Firebase Realtime Database
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Drinks");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String productId = snapshot.getKey();
                            String productName = snapshot.child("name").getValue(String.class);
                            String productImage = snapshot.child("image").getValue(String.class);
                            String productCategory = snapshot.child("category").getValue(String.class);
                            String productDesc = snapshot.child("descreption").getValue(String.class);
                            int productPrice = snapshot.child("price").getValue(Integer.class);


                            Drinks product = new Drinks(productName, productId, productImage, productPrice,productCategory,productDesc);
                            productList.add(product);
                        }
                        productAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
//public class ProductActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private ProductAdapter productAdapter;
//    private List<Product> productList;
//    private Button addButton;
//
//    private static final int ADD_PRODUCT_REQUEST_CODE = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_product);
//
//        recyclerView = findViewById(R.id.ProductRecycler);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        productList = new ArrayList<>();
//        productAdapter = new ProductAdapter(ProductActivity.this, productList);
//        recyclerView.setAdapter(productAdapter);
//
//        addButton = findViewById(R.id.add);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển sang AddProductActivity
//                Intent intent = new Intent(ProductActivity.this, AddProductActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Lấy dữ liệu sản phẩm từ Firebase Realtime Database
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String productId = snapshot.getKey();
//                    String productName = snapshot.child("name").getValue(String.class);
//                    String productImage = snapshot.child("image").getValue(String.class);
//                    int productPrice = snapshot.child("price").getValue(Integer.class);
//
//                    Product product = new Product(productName, productId, productImage, productPrice);
//                    productList.add(product);
//                }
//                productAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//}
