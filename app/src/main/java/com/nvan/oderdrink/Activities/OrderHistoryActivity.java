package com.nvan.oderdrink.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nvan.oderdrink.Adapter.DrinkAdapter;
import com.nvan.oderdrink.Adapter.OrderHistoryAdapter;
import com.nvan.oderdrink.MainActivity;
import com.nvan.oderdrink.ViewModel.AuthViewModel;
import com.nvan.oderdrink.ViewModel.OrderHistoryViewModel;
import com.nvan.oderdrink.databinding.ActivityHistoryorderBinding;

public class OrderHistoryActivity extends AppCompatActivity {
    private ActivityHistoryorderBinding binding;
    private OrderHistoryViewModel orderHistoryViewModel;
    private AuthViewModel authViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        initViewModel();
        initViews();
        initObserve();
        initListener();

        orderHistoryViewModel.getOrder(authViewModel.getUserId().getValue());
    }

    private void initListener() {
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("openInforFragment", true);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void initObserve() {
        orderHistoryViewModel.getMutableOrderList().observe(this, orderList -> {
            OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(orderList, OrderHistoryActivity.this);
            binding.rcvorderhistory.setAdapter(orderHistoryAdapter);
        });
    }

    private void initViews() {
        binding.rcvorderhistory.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this));
    }

    private void initViewModel() {
        orderHistoryViewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void initBinding() {
        binding = ActivityHistoryorderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
