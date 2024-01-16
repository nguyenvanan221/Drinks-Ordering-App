package com.nvan.oderdrink.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nvan.oderdrink.Model.Category;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.R;
import com.nvan.oderdrink.databinding.ListCategoryItemBinding;
import com.nvan.oderdrink.databinding.ListDrinksItemBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;

    private Context context;

    private int selectedItem = 0;

    private OnCategoryClickListener listener;
    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }


    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.categoryList = categoryList;
        this.context = context;
    }

    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListCategoryItemBinding binding = ListCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryAdapter.CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.binding.tvcategory.setText(category.getName());

        if (position == selectedItem){
            holder.binding.tvcategory.setBackgroundResource(R.drawable.botronedtclick);
            listener.onCategoryClick(category);
        }
        else {
            holder.binding.tvcategory.setBackgroundResource(R.drawable.botron_edt);
        }
        holder.binding.llcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onCategoryClick(category);
                selectedItem = position;
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ListCategoryItemBinding binding;
        public CategoryViewHolder(@NonNull ListCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
