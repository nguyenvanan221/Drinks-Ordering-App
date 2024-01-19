package com.nvan.oderdrink.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nvan.oderdrink.Activities.OrderHistoryActivity;
import com.nvan.oderdrink.Model.Drinks;
import com.nvan.oderdrink.Model.Order;
import com.nvan.oderdrink.databinding.ListDrinksItemBinding;
import com.nvan.oderdrink.databinding.ListOrderItemBinding;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private List<Order> ordersHistoryList;
    private Context context;

    public OrderHistoryAdapter(List<Order> ordersHistoryList, Context context) {
        this.ordersHistoryList = ordersHistoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListOrderItemBinding binding = ListOrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        Order order = ordersHistoryList.get(position);

        holder.binding.tvorderid.setText(order.getId());
        holder.binding.tvaddress.setText(order.getShippingAddress());
        holder.binding.tvtimeorder.setText(order.getOrderDate());
        holder.binding.tvtotalpriceorder.setText("" + order.getTotal());

        holder.binding.rcvdetailorderhistory.setLayoutManager(new LinearLayoutManager(context));
        DetailOrderHistoryAdapter detailAdapter = new DetailOrderHistoryAdapter(order.getDrinksList());
        holder.binding.rcvdetailorderhistory.setAdapter(detailAdapter);
    }

    @Override
    public int getItemCount() {
        return ordersHistoryList.size();
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        ListOrderItemBinding binding;

        public OrderHistoryViewHolder(@NonNull ListOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
