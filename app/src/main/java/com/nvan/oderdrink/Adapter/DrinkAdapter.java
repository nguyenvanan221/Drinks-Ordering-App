package com.nvan.oderdrink.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nvan.oderdrink.Activities.DrinkDetailActivity;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.databinding.ListDrinksItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    private final List<Drinks> drinksList;

    private final Context context;

    public DrinkAdapter(Context context, List<Drinks> drinksList) {
        this.drinksList = drinksList;
        this.context = context;
    }

    @NonNull
    @Override
    public DrinkAdapter.DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListDrinksItemBinding binding = ListDrinksItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DrinkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkAdapter.DrinkViewHolder holder, int position) {
        Drinks drink = drinksList.get(position);

        Glide.with(context).load(drink.getImage()).into(holder.binding.imgdrinks);
        holder.binding.tvname.setText(drink.getName());
        holder.binding.tvcategory.setText(drink.getCategory());

        holder.binding.rlayoutdrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DrinkDetailActivity.class);
                intent.putExtra("drinkId", drink.getId());
                intent.putExtra("drinkName", drink.getName());
                intent.putExtra("drinkImg", drink.getImage());
                intent.putExtra("drinkPrice", String.valueOf(drink.getPrice()));
                intent.putExtra("drinkDescreoption", drink.getDescreption());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinksList.size();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        ListDrinksItemBinding binding;
        public DrinkViewHolder(@NonNull ListDrinksItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
