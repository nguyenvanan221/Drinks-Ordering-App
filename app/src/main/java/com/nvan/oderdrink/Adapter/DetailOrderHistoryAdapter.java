package com.nvan.oderdrink.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Model.Order;
import com.nvan.oderdrink.databinding.ListDetailOrderhistoryBinding;
import com.nvan.oderdrink.databinding.ListOrderItemBinding;

import java.util.List;

public class DetailOrderHistoryAdapter extends RecyclerView.Adapter<DetailOrderHistoryAdapter.DetailOrderHistoryViewHolder>{

    private List<Drinks> drinkOrderList;

    public DetailOrderHistoryAdapter(List<Drinks> drinkOrderList) {
        this.drinkOrderList = drinkOrderList;
    }

    @NonNull
    @Override
    public DetailOrderHistoryAdapter.DetailOrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListDetailOrderhistoryBinding binding = ListDetailOrderhistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailOrderHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailOrderHistoryAdapter.DetailOrderHistoryViewHolder holder, int position) {
        Drinks drinks = drinkOrderList.get(position);
        holder.binding.tvdetailorder.setText(drinks.getName() + " ( " + drinks.getPrice() + " VND) "
                + " x " + drinks.getQuantity());
    }

    @Override
    public int getItemCount() {
        return drinkOrderList.size();
    }

    public static class DetailOrderHistoryViewHolder extends RecyclerView.ViewHolder {
        ListDetailOrderhistoryBinding binding;

        public DetailOrderHistoryViewHolder(@NonNull ListDetailOrderhistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
