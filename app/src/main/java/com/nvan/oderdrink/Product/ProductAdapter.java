package com.nvan.oderdrink.Product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Drinks> productList;

    public ProductAdapter(Context context, List<Drinks> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Drinks product = productList.get(position);

        // Hiển thị thông tin sản phẩm trong ViewHolder
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.valueOf(product.getPrice()));
        Glide.with(context)
                .load(product.getImage())
                .into(holder.imageView);

        // Xử lý sự kiện khi nhấn nút "Xóa"
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa sản phẩm
                deleteProduct(product);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Chỉnh sửa"
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang EditProductActivity để chỉnh sửa sản phẩm
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra("product", product);
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView priceTextView;
        public ImageView imageView;
        public Button deleteButton;
        public Button editButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_txt);
            priceTextView = itemView.findViewById(R.id.price_txt);
            imageView = itemView.findViewById(R.id.drink_img);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }


    private void deleteProduct(final Drinks product) {
        // Lấy ID của sản phẩm cần xóa
        final String productId = product.getId();

        // Xóa sản phẩm trong Firebase Realtime Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Drinks").child(productId);
        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Xóa sản phẩm thành công, cập nhật lại danh sách
                    productList.remove(product);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xóa sản phẩm thất bại
                    Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
