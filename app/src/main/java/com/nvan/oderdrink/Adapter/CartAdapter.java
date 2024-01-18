package com.nvan.oderdrink.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.ViewModel.AuthViewModel;
import com.nvan.oderdrink.ViewModel.CartViewModel;
import com.nvan.oderdrink.databinding.ListOrderDrinkItemBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final List<Drinks> drinkList;
    private final Context context;
    private final CartViewModel cartViewModel;
    private AuthViewModel authViewModel;

    public CartAdapter(Context context, List<Drinks> drinkList, CartViewModel cartViewModel) {
        this.drinkList = drinkList;
        this.context = context;
        this.cartViewModel = cartViewModel;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListOrderDrinkItemBinding binding = ListOrderDrinkItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Drinks drink = drinkList.get(position);

        Glide.with(context).load(drink.getImage()).into(holder.binding.imgdrinks);
        holder.binding.tvname.setText(drink.getName());
        holder.binding.tvprice.setText("" + drink.getPrice() );
        holder.binding.tvquantity.setText("" + drink.getQuantity());

        holder.binding.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int temp = Integer.parseInt( (String) holder.binding.tvquantity.getText() );
                temp = temp + 1;
                holder.binding.tvquantity.setText(Integer.toString(temp));
                cartViewModel.increaseQuantity(drink);
                Log.d("cart", "click plus" );
            }
        });

        holder.binding.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.decreaseQuantity(drink);
                int temp = Integer.parseInt( (String) holder.binding.tvquantity.getText() );
                temp = temp - 1;
                if (temp <= 0 ) {
                    cartViewModel.removeDrinkFromCart(authViewModel.getUserId().getValue(), drink.getId(), context);
                    return;
                }

                holder.binding.tvquantity.setText(Integer.toString(temp));
                Log.d("cart", "click minus" );
            }
        });

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        ListOrderDrinkItemBinding binding;
        public CartViewHolder(@NonNull ListOrderDrinkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
